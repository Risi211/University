package fr.unice.vicc;

import java.util.Comparator;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicy;
import org.cloudbus.cloudsim.power.PowerHost;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fhermeni2 on 16/11/2015.
 */
public class Energy extends VmAllocationPolicy {

    /** The map to track the server that host each running VM. */
    private Map<Vm,Host> hoster;

    public Energy(List<? extends Host> list) {
        super(list);
        hoster =new HashMap<>();
    }

    @Override
    protected void setHostList(List<? extends Host> hostList) {
        super.setHostList(hostList);
        hoster = new HashMap<>();
    }

    @Override
    public List<Map<String, Object>> optimizeAllocation(List<? extends Vm> list) {
        return null;
    }

    @Override
    public boolean allocateHostForVm(Vm vm) {        
        
        List<Host> hosts = getHostList();
        int num_hosts = hosts.size();
        
        //order the hosts on the new composite dimension "z"
        //z is the summation of the Ram and Mips
        //after this sorting, we will have a list where the first host
        //has the maximum value of z, so it is ordered in a decreasing way
        hosts.sort(new Comparator<Host>(){
            public int compare(Host h1, Host h2)
                {
                    List<Vm> vms1 = h1.getVmList();
                    int vmRam1 = 0;
                    for(Vm v:vms1)
                    {
                        vmRam1 += v.getRam(); //sum all ram utilized from the virtual machines of the hos h1
                    }
                    int availableRam1 = h1.getRam() - vmRam1;
                    
                    List<Vm> vms2 = h2.getVmList();
                    int vmRam2 = 0;
                    for(Vm v:vms2)
                    {
                        vmRam2 += v.getRam(); //sum all ram utilized from the virtual machines of the hos h2
                    }
                    int availableRam2 = h2.getRam() - vmRam2;
                    
                    double z1 = availableRam1 + h1.getAvailableMips();
                    double z2 = availableRam2 + h2.getAvailableMips();
                    return Double.compare(z1, z2);
                }
        });
        //we start always from the last index
        for(int i = 0; i < num_hosts; i++)
        {
            Host h = hosts.get(i);
            if(h.vmCreate(vm))
            { 
                hoster.put(vm, h);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean allocateHostForVm(Vm vm, Host host) {
        //this code is equal to the naive scheduler,
        //because the host is fixed
        if(host.vmCreate(vm))
        {
            hoster.put(vm, host);
            return true;
        }
        return false;
    }

    @Override
    public void deallocateHostForVm(Vm vm) {
        
        if(hoster.containsKey(vm))
        {
            Host h = hoster.remove(vm);
            h.vmDestroy(vm);            
        }

    }

    @Override
    public Host getHost(Vm vm) {
        return hoster.get(vm);
    }

    @Override
    public Host getHost(int vmId, int userId) {
     
        Set<Vm> vms = hoster.keySet();
        for(Vm v:vms)
        {
            if(v.getId() == vmId && v.getUserId() == userId)
            {
                return hoster.get(v);
            }
        }
        return null;
    }
}
