package com.dcs.bnotified.ONP.process;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.ApplicationContext;

import com.dcs.bnotified.ONP.Dispatcher;
import com.dcs.bnotified.ONP.dao.CustRegistrationDAO;
import com.dcs.bnotified.ONP.data.CustRegistrationVO;

public class NewCustRegProcessor implements ItemProcessor<CustRegistrationVO, CustRegistrationVO> {

	public CustRegistrationVO process(CustRegistrationVO custregVO) throws Exception {

		ApplicationContext context = Dispatcher.getContext();
		CustRegistrationDAO custregDAO = (CustRegistrationDAO) context.getBean("CustRegistrationDAO");
		custregDAO.insert(custregVO);
		return null;
	}

}
