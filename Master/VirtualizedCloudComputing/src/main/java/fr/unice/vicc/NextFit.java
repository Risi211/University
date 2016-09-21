package fr.unice.vicc;

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
public class NextFit extends VmAllocationPolicy {

    /** The map to track the server that host each running VM. */
    private Map<Vm,Host> hoster;
    private int last_index = 0;

    public NextFit(List<? extends Host> list) {
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
        int end_hosts = num_hosts;
        //we start always from the last index
        for(int i = last_index; i < end_hosts; i++)
        {
            Host h = hosts.get(i);
            if(h.vmCreate(vm))
            { 
                hoster.put(vm, h);
                last_index = i; //next time, we will start the search form the next host (ref: https://www.quora.com/What-are-the-first-fit-next-fit-and-best-fit-algorithms-for-memory-management)    
                return true;
            }
            //reset the last_index if it arrives at the end
            if(last_index == num_hosts - 1)
            {
                last_index = 0;
                end_hosts = last_index - 1; //we will stop at the previous one
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
