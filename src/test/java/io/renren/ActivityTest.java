package io.renren;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.renren.modules.activity.controller.ActivityController;
import io.renren.modules.activity.entity.Activity;
import io.renren.modules.activity.service.ActivityService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityTest {

	@Autowired
	private ActivityController activityController;
	
	//@Test
	public void save(){
		Activity activity = new Activity();
		activityController.save(activity);
	}
	
	@Test
     public void Test3() {
         System.out.println("12.5的四舍五入值：" + Math.round(12.5));
         System.out.println("-12.5的四舍五入值：" + Math.round(-12.5));
     }
 
     @Test
     public void Test4() {
         BigDecimal d = new BigDecimal(100000); // 存款
         BigDecimal r = new BigDecimal(0.001875 * 3); // 利息
         BigDecimal i = d.multiply(r).setScale(2, RoundingMode.HALF_EVEN); // 使用银行家算法
 
         System.out.println("季利息是：" + i);
     }
 
     @Test
     public void Test5() {
         double f = 111231.5585;
         BigDecimal b = new BigDecimal(f);
         double f1 = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
         System.out.println("f1:" + f1);
     }
 
     @Test
     public void Test6() {
         java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
         String s1 = df.format(3.1415926);
         String s2 = df.format(3.1465926);
         System.out.println("s:" + s1);
         System.out.println("s:" + s2);
     }
 
     @Test
     public void Test7() {
 
         String s = String.format("%.2f",3.1415926);
         String s1 = String.format("%.2f",3.1465926);
         System.out.println("S:"+s);
         System.out.println("S:"+s1);
     
     }
}
