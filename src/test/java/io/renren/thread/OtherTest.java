package io.renren.thread;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OtherTest {

	//main 方法测试 i=i++、i++
	public static void main(String[] args) {
//		int i = 10;
//		int j = 10;
//		i=i++;
//		int k = i++;
//		j++;
//		System.out.println("i值:"+i);
//		System.out.println("k值:"+k);
//		System.out.println("j值:"+j);
		
		
		MyThread1 mt = new MyThread1();
		Thread t1 = new Thread(mt, "一号窗口");
		Thread t2 = new Thread(mt, "二号窗口");
		Thread t3 = new Thread(mt, "三号窗口");
		//Thread t4 = new Thread(mt, "四号窗口");
		t1.start();
		t2.start();
		t3.start();
		//t4.start();
		
	}
	
	//多线程  火车买票
	//@Test
//	public void testThred(){
//		MyThread1 mt = new MyThread1();
//		Thread t1 = new Thread(mt, "一号窗口");
//		Thread t2 = new Thread(mt, "二号窗口");
//		Thread t3 = new Thread(mt, "三号窗口");
//		Thread t4 = new Thread(mt, "四号窗口");
//		t1.start();
//		t2.start();
//		t3.start();
//		t4.start();
//	}
	
	
}
