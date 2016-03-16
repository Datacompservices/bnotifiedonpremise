package com.dcs.bnotified.onpservice.message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.dcs.bnotidfied.onpservice.util.ConnectionManager;
import com.dcs.bnotidfied.onpservice.util.PropertyManager;
import com.dcs.bnotified.ONP.data.CustRegistrationVO;
import com.dcs.bnotified.onpservice.dao.AuditDAO;
import com.dcs.bnotified.onpservice.dao.BroadcastMessageDAO;
import com.dcs.bnotified.onpservice.dao.CustRegistrationDAO;
import com.dcs.bnotified.onpservice.dao.MessageFormatDAO;
import com.dcs.bnotified.onpservice.data.BroadcastMessageVO;
import com.dcs.bnotified.onpservice.data.DashboardVO;
import com.dcs.bnotified.onpservice.data.MessageFormatVO;
import com.dcs.bnotified.onpservice.data.PushMessageVO;
import com.dcs.bnotified.onpservice.data.QuartzDetailsVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.postgresql.util.PSQLException;

import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Path("msgservice")
public class MessageService {

	@Produces({ "application/json" })
	@GET
	@Path("batchexecutionstatus")
	public String getBatchExecutionStatus() throws SQLException, Exception {
		MessageFormatDAO msgformatdao = new MessageFormatDAO();
		List<QuartzDetailsVO> returnlist = msgformatdao.quartzTriggerDetails();

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		DashboardVO dashboardVO = homePageDetails();
		dashboardVO.setQuartzDet(returnlist);
		String finalgson = gson.toJson(dashboardVO);
		// String finalgson = gson.toJson(returnlist);
		System.out.println(finalgson);
		return finalgson;
	}

	// @GET
	// @Path("homepagedetails")
	public DashboardVO homePageDetails() throws SQLException, Exception {

		MessageFormatDAO msgformatdao = new MessageFormatDAO();
		DashboardVO dashboardVO = new DashboardVO();
		// dashboardVO.setNoofcustomers();
		dashboardVO.setMsgsenttodaycount(msgformatdao.MessageSentTodayCount());
		dashboardVO.setMsgtotalcount(msgformatdao.totalMessageCount());
		dashboardVO.setNoofmessages(msgformatdao.getMessageFormatCount());

		return dashboardVO;

	}

	@Produces({ "application/json" })
	@GET
	@Path("listmessageformats")
	public String getAllMessageFormats() throws SQLException, Exception {
		MessageFormatDAO msgformatdao = new MessageFormatDAO();
		List<MessageFormatVO> returnlist = msgformatdao.findAllMessageformats();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String finalgson = gson.toJson(returnlist);

		return finalgson;
	}

	@Produces({ "application/text" })
	@GET
	@Path("insertmessageformat/{message}/{branch}/{rcreid}")
	public String insertMessageFormat(@PathParam("message") final String message,
			@PathParam("branch") final String branch, @PathParam("rcreid") final String rcreid,
			@Context HttpServletRequest req) throws SQLException, Exception {
		Vector<String> batchstatus = new Vector<String>();
		AuditDAO auditdao = new AuditDAO();

		try {
			new PropertyManager().PRPMValList();

			MessageFormatVO msgformatVO = new MessageFormatVO();

			msgformatVO.setBankID(PropertyManager.PRPMVal.get("BANK_ID"));
			msgformatVO.setBranchID(PropertyManager.PRPMVal.get("DEFAULT_BRANCH_ID"));
			msgformatVO.setMsg_Channel_ID(PropertyManager.PRPMVal.get("CHANNEL_ID"));
			msgformatVO.setMessageText(message);
			msgformatVO.setMsg_lang("en-us");
			msgformatVO.setDel_flg("N");
			msgformatVO.setR_cre_id(rcreid);
			msgformatVO.setR_mod_id(rcreid);
			MessageFormatDAO msgformatDAO = new MessageFormatDAO();
			batchstatus.add("UserId [" + msgformatVO.getR_cre_id() + "] submited text [" + msgformatVO.getMessageText()
					+ "] to insertMessageFormat");
			msgformatDAO.insert(msgformatVO);

			batchstatus.add("insertMessageFormat service executed successfully");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				auditdao.insertAudit(getIpAddr(req), "getUserProfile", batchstatus, "A");
			} catch (Exception e) {
				System.out.println("While doing audit exception Thworn");
				e.printStackTrace();
			}

		}

		return "SUCCESS";

	}

	@Produces({ "application/text" })
	@GET
	@Path("insertcustommessagepush/{message}/{rcreid}")
	public String insertCustomPushMessage(@PathParam("message") final String message,
			@PathParam("rcreid") final String rcreid, @Context HttpServletRequest req) throws SQLException, Exception {

		Vector<String> batchstatus = new Vector<String>();
		AuditDAO auditdao = new AuditDAO();

		try {
			new PropertyManager().PRPMValList();
			PushMessageVO pushmsgVO = new PushMessageVO();

			pushmsgVO.setBankid(PropertyManager.PRPMVal.get("BANK_ID"));
			pushmsgVO.setBranchid(PropertyManager.PRPMVal.get("DEFAULT_BRANCH_ID"));
			pushmsgVO.setChannelid(PropertyManager.PRPMVal.get("CHANNEL_ID"));
			pushmsgVO.setTextmsg(message);
			pushmsgVO.setMsgdelflag("N");
			pushmsgVO.setRcreid(rcreid);
			batchstatus.add("UserId [" + pushmsgVO.getRcreid() + "] submited text [" + pushmsgVO.getTextmsg()
					+ "] to insertCustomPushMessage");

			MessageFormatDAO msgformatDAO = new MessageFormatDAO();
			msgformatDAO.insertPushMessage(pushmsgVO);
			batchstatus.add("insertCustomPushMessage service executed successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				auditdao.insertAudit(getIpAddr(req), "insertCustomPushMessage", batchstatus, "A");
			} catch (Exception e) {
				System.out.println("While doing audit exception Thworn");
				e.printStackTrace();
			}

		}

		return "SUCESS";

	}

	@Produces(MediaType.TEXT_PLAIN)
	@GET
	@Path("resendpushmessage/{mobileNumber}/{message}/{rcreid}")
	public String insertCustomMessageUpload(@PathParam("mobileNumber") final String mobileNumber,
			@PathParam("message") final String message, @PathParam("rcreid") final String rcreid,
			@Context HttpServletRequest req) throws SQLException, Exception {

		// public String insertCustomMessageUpload(PushVO pushVO, @Context
		// HttpServletRequest req) throws SQLException, Exception {
		Vector<String> batchstatus = new Vector<String>();
		AuditDAO auditdao = new AuditDAO();

		try {
			new PropertyManager().PRPMValList();

			BroadcastMessageVO BroadcastMsgVO = new BroadcastMessageVO();
			BroadcastMsgVO.setMsgID("0"); // Message Id is 0 for all custom
											// message push messages.
			BroadcastMsgVO.setBankID(PropertyManager.PRPMVal.get("BANK_ID"));
			BroadcastMsgVO.setEntityName(PropertyManager.PRPMVal.get("ENTITY_SHORTNAME"));
			BroadcastMsgVO.setBranchID(PropertyManager.PRPMVal.get("DEFAULT_BRANCH_ID"));
			BroadcastMsgVO.setChannel_id(PropertyManager.PRPMVal.get("CHANNEL_ID"));
			BroadcastMsgVO.setMsgText(message);
			BroadcastMsgVO.setRcreID(rcreid);
			BroadcastMsgVO.setMsg_delivery_flg(false);

			String arr[] = mobileNumber.split(",");

			for (int i = 0; i < arr.length; i++) {
				BroadcastMsgVO.setMobile_number(Long.parseLong(arr[i]));
				new BroadcastMessageDAO().insert(BroadcastMsgVO);
				batchstatus.add("MobileNumber: " + arr[i] + " Message: " + (String) message + " CreatedBy " + rcreid);
			}

			batchstatus.add("resendpushmessage service executed successfully");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				auditdao.insertAudit(getIpAddr(req), "resendpushmessage", batchstatus, "A");
			} catch (Exception e) {
				System.out.println("Exception from resendpushmessage");
				e.printStackTrace();
			}
		}

		return "SUCESS";

	}

	public void insertPushRecord(String message, String mobileNumber, String rcreid) {
		Vector<String> batchstatus = new Vector<String>();
		AuditDAO auditdao = new AuditDAO();

		try {
			new PropertyManager().PRPMValList();

			BroadcastMessageVO BroadcastMsgVO = new BroadcastMessageVO();
			BroadcastMsgVO.setMsgID("0"); // Message Id is 0 for all custom
											// message push messages.
			BroadcastMsgVO.setBankID(PropertyManager.PRPMVal.get("BANK_ID"));
			BroadcastMsgVO.setEntityName(PropertyManager.PRPMVal.get("ENTITY_SHORTNAME"));
			BroadcastMsgVO.setBranchID(PropertyManager.PRPMVal.get("DEFAULT_BRANCH_ID"));
			BroadcastMsgVO.setChannel_id(PropertyManager.PRPMVal.get("CHANNEL_ID"));
			BroadcastMsgVO.setMsgText(message);
			BroadcastMsgVO.setRcreID(rcreid);
			BroadcastMsgVO.setMsg_delivery_flg(false);

			String arr[] = mobileNumber.split(",");

			System.out.println("printing the values of mobileNumbers are");
			Set<Long> set = new HashSet<Long>();

			for (int i = 0; i < arr.length; i++) {
				System.out.println(arr[i]);
				if (arr[i].trim().length() > 0) {
					set.add(new Long(arr[i].trim()));
				}
			}
			Iterator<Long> it = set.iterator();

			String temp = "";
			while (it.hasNext()) {
				// for (int i = 0; i < arr.length; i++) {
				temp = it.next().toString().trim();
				if (temp.length() > 0) {
					BroadcastMsgVO.setMobile_number(Long.parseLong(temp));
					new BroadcastMessageDAO().insert(BroadcastMsgVO);
					batchstatus.add("MobileNumber: " + temp + " Message: " + (String) message + " CreatedBy " + rcreid);
				}
			}

			batchstatus.add("insertPushRecord service executed successfully");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				auditdao.insertAudit("0.0.0.0", "resendpushmessage", batchstatus, "A");
			} catch (Exception e) {
				System.out.println("Exception from resendpushmessage");
				e.printStackTrace();
			}
		}

	}

	private void insertNewCustomer(String mobileNumbers) throws SQLException, Exception {

		CustRegistrationVO custregVO = new CustRegistrationVO();
		custregVO.setBank_ID(PropertyManager.PRPMVal.get("BANK_ID"));
		custregVO.setBranch_ID(PropertyManager.PRPMVal.get("DEFAULT_BRANCH_ID"));
		custregVO.setAccount_number("1001");
		custregVO.setCust_id("1001");
		custregVO.setCust_category("GOLD");
		custregVO.setCust_enabled(false);
		custregVO.setDel_flg("N");
		custregVO.setR_cre_id("BATCH");
		custregVO.setR_mod_id("BATCH");
		custregVO.setFreetext1("");
		custregVO.setFreetext2("");
		custregVO.setFreetext3("");

		String arr[] = mobileNumbers.split(",");
		String temp = "";
		Set<Long> set = new HashSet<Long>();
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
			if (arr[i].trim().length() > 0) {
				set.add(new Long(arr[i].trim()));
			}
		}
		Iterator<Long> it = set.iterator();

		// for (int i = 0; i < arr.length; i++) {
		while (it.hasNext()) {

			CustRegistrationDAO cust = new CustRegistrationDAO();
			temp = it.next().toString().trim();
			if (temp.trim().length() > 0) {
				System.out.println(temp);
				cust.insert(Long.parseLong(temp), custregVO);
			}
		}

	}

	@POST
	@Path("/dnduploadfile")
	@Consumes("multipart/form-data")
	public Response dnduploadFile(MultipartFormDataInput input) throws SQLException, Exception {

		new PropertyManager().PRPMValList();
		String fileName = "";
		String UPLOADED_FILE_PATH = PropertyManager.PRPMVal.get("UPLOAD_FILE_PATH");
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		@SuppressWarnings("deprecation")
		Map<String, InputPart> formdata = input.getFormData();

		List<InputPart> inputParts = uploadForm.get("dnduploadedFile");

		for (InputPart inputPart : inputParts) {

			try {

				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = getFileName(header);

				// convert the uploaded file to inputstream
				InputStream inputStream = inputPart.getBody(InputStream.class, null);

				byte[] bytes = IOUtils.toByteArray(inputStream);

				String extension = "";

				int i = fileName.lastIndexOf('.');
				int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

				if (i > p) {
					extension = fileName.substring(i + 1);
				}
				System.out.println(extension);
				// constructs upload file path
				fileName = UPLOADED_FILE_PATH + fileName;
				System.out.println(fileName);

				writeFile(bytes, fileName);
				String mobileNumbers = new String(bytes, "UTF-8");
				System.out.println("mobile Numbers are ");
				System.out.println(mobileNumbers);

				if ((extension.trim().equals("txt")) || (extension.trim().equals("csv"))) {
					System.out.println("inside loop " + extension + " is ");

					insertdndRecord(formdata.get("dndaction").getBodyAsString().toString(), mobileNumbers,
							formdata.get("rcreid").getBodyAsString().toString());

				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		String html = "<html><head><script> function goBack() { window.history.back();} </script></head><body onLoad='goBack()'> uploadFile is called, Uploaded file name : "
				+ fileName + "</body></html>";
		return Response.status(200).entity(html).build();
	}

	private void insertdndRecord(String dndaction, String mobileNumbers, String rcreid) {
		// TODO Auto-generated method stub

		System.out.println("dndaction" + dndaction);
		System.out.println("mobileNumber" + mobileNumbers);
		System.out.println("rcreid" + rcreid);

		Vector<String> batchstatus = new Vector<String>();
		AuditDAO auditdao = new AuditDAO();
		String query = "";
		if (dndaction.trim().equals("REGISTER")) {
			query = "insert into ONP_DND_MOBILE_REG values (?)";
		} else {
			query = "DELETE FROM ONP_DND_MOBILE_REG WHERE mobileNumber =?";
		}
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		Connection con = null;
		Connection con1 = null;
		String arr[] = mobileNumbers.split(",");
		try {
			con = ConnectionManager.getConnection();
			con1 = ConnectionManager.getConnection("HOST");
			ps = con.prepareStatement(query);
			ps1 = con1.prepareStatement(query);
			con.setAutoCommit(true);
			con1.setAutoCommit(true);
			System.out.println("printing the values of mobileNumbers are");
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].trim().length() > 0) {
					try {
						ps.setLong(1, Long.parseLong(arr[i].trim()));
						ps.execute();

						ps1.setLong(1, Long.parseLong(arr[i].trim()));
						ps1.execute();
					} catch (PSQLException psql) {
						System.out.println("SQLState is " + psql.getSQLState());
						if (psql.getErrorCode() == 23505) {
							batchstatus.addElement("REC ALLREADY EXISTS" + arr[i].trim());
							continue;
						} else {
							psql.printStackTrace();
						}

					}
				}
				batchstatus.addElement("NEW REC" + arr[i].trim());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
				if (con1 != null) {
					con1.close();
					con1 = null;
				}

				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (ps1 != null) {
					ps1.close();
					ps1 = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		try {
			auditdao.insertAudit("0.0.0.0", "insertdndRecord", batchstatus, "A");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@POST
	@Path("/uploadfile")
	@Consumes("multipart/form-data")
	public Response uploadFile(MultipartFormDataInput input) throws SQLException, Exception {

		String fileName = "";

		new PropertyManager().PRPMValList();

		String UPLOADED_FILE_PATH = PropertyManager.PRPMVal.get("UPLOAD_FILE_PATH");

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

		@SuppressWarnings("deprecation")
		Map<String, InputPart> formdata = input.getFormData();

		// System.out.println("Hi from Me");
		// System.out.println(formdata.get("message").getBodyAsString().toString());
		// System.out.println(formdata.get("rcreid").getBodyAsString().toString());
		// System.out.println("Hi from Me Ends here .");

		String tmobileNumbers = formdata.get("mobileNumbers").getBodyAsString().toString();
		System.out.println("mobile Numbers entered are :" + tmobileNumbers);

		if (tmobileNumbers.trim().length() > 0 && tmobileNumbers != null) {
			insertPushRecord(formdata.get("message").getBodyAsString().toString(), tmobileNumbers,
					formdata.get("rcreid").getBodyAsString().toString());
			insertNewCustomer(tmobileNumbers);
		}

		// System.out.println("prinitng the file part .....");
		// System.out.println(uploadForm.get("uploadedFile").isEmpty());
		// System.out.println("prinitng the file part ends here .....");

		if (formdata.get("uploadedFile").getBodyAsString() != null) {

			List<InputPart> inputParts = uploadForm.get("uploadedFile");

			for (InputPart inputPart : inputParts) {

				try {

					MultivaluedMap<String, String> header = inputPart.getHeaders();
					fileName = getFileName(header);

					// convert the uploaded file to inputstream
					InputStream inputStream = inputPart.getBody(InputStream.class, null);

					byte[] bytes = IOUtils.toByteArray(inputStream);

					// constructs upload file path
					fileName = UPLOADED_FILE_PATH + fileName;
					String extension = "";

					int i = fileName.lastIndexOf('.');
					int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

					if (i > p) {
						extension = fileName.substring(i + 1);
					}
					System.out.println(extension);

					writeFile(bytes, fileName);
					String mobileNumbers = new String(bytes, "UTF-8");
					System.out.println("mobile Numbers are ");
					System.out.println(mobileNumbers);

					if ((extension.equals("txt")) || (extension.equals("csv"))) {
						insertPushRecord(formdata.get("message").getBodyAsString().toString(), mobileNumbers,
								formdata.get("rcreid").getBodyAsString().toString());
						insertNewCustomer(mobileNumbers);

					} else if ((extension.equals("xls")) || (extension.equals("xlsx"))) {
						try {
							String tempmobileNumbers = processXLS(fileName);
							insertNewCustomer(tempmobileNumbers);
							insertPushRecord(formdata.get("message").getBodyAsString().toString(), tempmobileNumbers,
									formdata.get("rcreid").getBodyAsString().toString());

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					System.out.println("Done");

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

		String html = "<html><head><script> function goBack() { window.history.back();} </script></head><body onLoad='goBack()'> uploadFile is called, Uploaded file name : "
				+ fileName + "</body></html>";
		return Response.status(200).entity(html).build();

	}

	static XSSFRow row;

	private String processXLS(String filePath) throws IOException, Exception {
		String mobileNumbers = "";
		long l = 0;
		FileInputStream fis = new FileInputStream(new File(filePath));
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet spreadsheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = spreadsheet.iterator();

		Set<Long> set = new HashSet<Long>();

		while (rowIterator.hasNext()) {
			row = (XSSFRow) rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
					l = (new Double(cell.getNumericCellValue())).longValue();
					set.add(new Long(l));
					System.out.print(l + " \t\t ");
					break;
				case Cell.CELL_TYPE_STRING:
					System.out.print(cell.getStringCellValue() + " \t\t ");
					break;
				}
			}

		}

		fis.close();

		Iterator<Long> it = set.iterator();
		while (it.hasNext()) {
			mobileNumbers += (it.next().toString()) + ",";
		}

		System.out.println("printing all mobile Numbers are :");
		System.out.println(mobileNumbers);

		return mobileNumbers;
	}

	// save to somewhere
	private void writeFile(byte[] content, String filename) throws IOException {

		File file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(content);
		fop.flush();
		fop.close();

	}

	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			System.out.println(ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			System.out.println(ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			System.out.println(ip);
		}
		return ip;
	}

}
