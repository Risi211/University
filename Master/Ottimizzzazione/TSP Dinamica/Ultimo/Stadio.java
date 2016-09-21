/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lowerbound;

import java.util.*;
//permette di avere più values per una stessa key, gestiti come liste
import com.google.common.collect.LinkedListMultimap;


/**
 *
 * @author luca
 */
public class Stadio {

    //long NUMERO_PRIMO = 99991;    
    long NUMERO_PRIMO =  4000037;    
    
    //List<Stato> stati = new ArrayList<Stato>();
    //List<Stato>[] stati; //ogni bucket indica il vertice end dello stato
    List<LinkedListMultimap<Long, Stato>> stati;
    
    public Stadio(int num_vertici)
    {
        //stati = (List<Stato>[])new ArrayList[num_vertici]; //se metti Stato al posto di ? da errore
//        stati = new LinkedHashMap<Integer, Stato>[num_vertici];
        stati = new ArrayList<>(num_vertici);
        
        for(int i = 0; i < num_vertici; i++)
        {
            //stati[i] = new ArrayList<Stato>();
            //stati.add(i, new LinkedHashMap<>());
            //LinkedListMultimap<Integer, Integer> ll = LinkedListMultimap.create();
            stati.add(i, LinkedListMultimap.create());
        }
    }
    
    public void addStato(Stato s)
    {
        //stati[s.end].add(s);
        long key = s.set % NUMERO_PRIMO;
        stati.get(s.end).put(key, s);
    }
    
    public Stato getStato(Stato s)
    {
        //lo stato s appartiene a questo stadio se trova uno stato che:
        //1) ha lo stesso vertice finale
        //AND
        //2) ha lo stesso set di vertici (la equalSet controlla che il set contenga gli stessi vertici)        
        
        //return stati[s.end].stream().filter((Stato t) -> (t.end == s.end) && (equalSet(t.set, s.set))).findAny();
        long key = s.set % NUMERO_PRIMO;
        //return stati.get(s.end).get(key).stream().filter((Stato t) -> (t.end == s.end) && (equalSet(t.set, s.set))).findAny();
        List<Stato> set = stati.get(s.end).get(key);
        for(Stato t:set)
        {
            if(t.end == s.end && equalSet(t.set, s.set))
            {
                return t;
            }
        }
        return null;
    }
    
    private boolean equalSet(long set1, long set2)
    {        
        return set1 == set2;
    }
    
    public void updateStato(Stato s_new, Stato s_old)
    {
        int bucket = s_old.end;
        long key = s_old.set % NUMERO_PRIMO;
        List<Stato> setStati = stati.get(bucket).get(key);
        int count = setStati.size();
        for(int i = 0; i < count; i++)
        {
            if(setStati.get(i).set == s_old.set)
            {
                //stati.get(bucket).remove(key, s_old);               
               //nuovo stato
               Stato target = setStati.get(i);
               target.predecessore = s_new.predecessore;
               target.costo = s_new.costo;
               //stati.get(bucket).put(key, target);               
               break;
            }
        }        
        /*
        int bucket = s_old.end;
        int count = stati[bucket].size();
        for(int i = 0; i < count; i++)
        {
            if(stati[bucket].get(i).id == s_old.id)
            {
               Stato target = stati[bucket].get(i);
               target.predecessore = s_new.predecessore;
               target.costo = s_new.costo;
               break;
            }
        }
                */
        
    }
        
}
