package voucher;

import java.util.ArrayList;
import java.util.List;

import com.rmi.server.entity.ImageData;
import com.voucher.manage.tools.MyTestUtil;

public class WeiXinTest {

	
	public static void main(String[] args) {
		
		List<ImageData> imageDataList=new ArrayList<>();
		ImageData imgdata1 = new ImageData();
		imgdata1.setName("安全巡查照片");
		imgdata1.setURI("59c8708cdc88adf6858fbfb88198d82e1566977895704");
		ImageData imgdata2 = new ImageData();
		imgdata2.setName("安全巡查照片2");
		imgdata2.setURI("59c8708cdc88adf6858fbfb88198d82e1566977895704");
		imageDataList.add(imgdata1);
		imageDataList.add(imgdata2);
		MyTestUtil.print(imageDataList);
	}

}
