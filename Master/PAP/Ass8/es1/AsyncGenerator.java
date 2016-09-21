package ppp;

import java.util.Random;
import rx.Subscriber;

public class AsyncGenerator extends Thread {

	private Subscriber<? super Double> subscriber;
        private int freq;
        private TempSensor sensore;
	
	public AsyncGenerator(Subscriber<? super Double> subscriber, int freq, TempSensor sensore){
		this.subscriber = subscriber;
                                this.freq = freq;
                                this.sensore = sensore;
	}
	
	@Override
	public void run() 
                {                 
                    while(true)
                    {
                            try 
                            {
                                Thread.sleep(freq);
                                subscriber.onNext(sensore.getCurrentValue());
                            }
                            catch (Exception ex)
                            {
                                subscriber.onError(ex);
                            }
                    } 
                      //  subscriber.onCompleted();
                        
	}
}
