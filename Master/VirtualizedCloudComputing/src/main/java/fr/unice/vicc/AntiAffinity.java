/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Luca
 */
public class AntiAffinity extends VmAllocationPolicy {

    /** The map to track the server that host each running VM. */
    private Map<Vm,Host> hoster;

    public AntiAffinity(List<? extends Host> list) {
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
        int range = vm.getId() / 100; //obtains the range (for example, 150 / 100 = 1) 
        List<Host> hosts = getHostList();
        for(Host h:hosts)
        {
            List<Vm> vms = h.getVmList(); //all vms of this host
            boolean ok = true; //if this var is true, we can add this vm inside this host            
            //we check if this host contains one VM that is anti-affined
            for(Vm v:vms)
            {
                int v_range = v.getId() / 100; //get range of current vm of this host
                if(v_range == range) 
                {
                    //we check every range of vm, at the first incompatibility we change host
                    ok = false;
                    break;
                }
            }
            if(ok) //if every vm on this host has a different range from the new one, we can add it on this host
            {
                if(h.vmCreate(vm)) //if this host can take this vm (it has enough memory, cpu,....)
                { 
                    hoster.put(vm, h);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean allocateHostForVm(Vm vm, Host host) {
        int range = vm.getId() / 100; //obtains the range (for example, 150 / 100 = 1) 
        List<Vm> vms = host.getVmList(); //all vms of this host
        boolean ok = true; //if this var is true, we can add this vm inside this host            
        //we check if this host contains one VM that is anti-affined
        for(Vm v:vms)
        {
            int v_range = v.getId() / 100; //get range of current vm of this host
            if(v_range == range) 
            {
                //we check every range of vm, at the first incompatibility we change host
                ok = false;
                break;
            }
        }
        if(ok) //if every vm on this host has a different range from the new one, we can add it on this host
        {
            if(host.vmCreate(vm)) //if this host can take this vm (it has enough memory, cpu,....)
            { 
                hoster.put(vm, host);
                return true;
            }
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
