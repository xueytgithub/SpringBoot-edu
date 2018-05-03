package io.renren.thread;

import java.util.Date;

public class MyThread1 implements Runnable{

	private int ticket = 20;
	private String name;
	@Override
	public void run() {
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		//开始时间
		Date start = new Date();
		for (int i = 0; i < 5000; i++) {
			if(this.ticket>0){
				
				if("一号窗口".equals(Thread.currentThread().getName())){
					count1++;
				}else if("二号窗口".equals(Thread.currentThread().getName())){
					count2++;
				}else if("三号窗口".equals(Thread.currentThread().getName())){
					count3++;
				}
				this.ticket--;
				//System.err.println(Thread.currentThread().getName()+"买票："+(this.ticket--));
			}
		}
		//结束时间
		Date end = new Date();
		Long time = (end.getTime() - start.getTime());
		System.out.println(Thread.currentThread().getName()+"售馨用时："+time+"毫秒"+" 买"+count1+"**"+count2+"**"+count3+"张票");
//		System.out.println(Thread.currentThread().getName()+"售馨用时："+time+"毫秒"+" 买"+count2+"张票");
//		System.out.println(Thread.currentThread().getName()+"售馨用时："+time+"毫秒"+" 买"+count3+"张票");
	}
	
	
	public int getTicket() {
		return ticket;
	}
	public void setTicket(int ticket) {
		this.ticket = ticket;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

}
