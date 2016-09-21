package fr.unice.vicc;

import org.cloudbus.cloudsim.VmAllocationPolicy;
import org.cloudbus.cloudsim.power.PowerHost;

import java.util.List;

/**
 * @author Fabien Hermenier
 */
public class VmAllocationPolicyFactory {

    /**
     * Return the VMAllocationPolicy associated to id
     * @param id the algorithm identifier
     * @param hosts the host list
     * @return the selected algorithm
     */
    VmAllocationPolicy make(String id, List<PowerHost> hosts) {

        switch (id) {
            case "naive":  return new NaiveVmAllocationPolicy(hosts);
            case "antiAffinity":  return new AntiAffinity(hosts);
            case "nextFit":  return new NextFit(hosts);
            case "worstFit":  return new WorstFit(hosts);
            case "noViolations":  return new NoViolations(hosts);
            case "energy":  return new Energy(hosts);
            case "greedy":  return new Greedy(hosts);
        }
        throw new IllegalArgumentException("No such policy '" + id + "'");
    }
}
