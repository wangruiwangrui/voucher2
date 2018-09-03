package com.voucher.manage.file;

import java.util.List;

public class XlsFileFactory extends AbstractFileUpload{

	@Override
	public Integer upload(Object object,String GUID, List<String> names, List<byte[]> files) {
		// TODO Auto-generated method stub
		return uploadFile(object,GUID, names, files);
	}
	

}
