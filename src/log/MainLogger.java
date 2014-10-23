package log;

import view.MyLayoutView;

public class MainLogger {

	public static String LOG = "";

	public MainLogger(){

	}

	public static void cleanLOG(){
		LOG = "";
		MyLayoutView.textArea.setText(LOG);		
	}

	public static String printLOG(){
		System.out.println(LOG);
		return LOG;
	}

	public static void addLOG(String x){
//		synchronized(MyLayoutView.textArea.getText()){
//			synchronized(LOG){
				LOG = LOG + "\n" + x;
				MyLayoutView.textArea.setText(LOG);
//				LOG.notify();
//			}
//			try{
//				MyLayoutView.textArea.getText().wait();
//			}catch(InterruptedException e){
//				e.printStackTrace();
//			}
//		}
	}
}