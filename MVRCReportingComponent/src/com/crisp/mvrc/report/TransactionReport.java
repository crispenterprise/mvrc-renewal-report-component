package com.crisp.mvrc.report;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 * Servlet implementation class TransactionReport
 */
@WebServlet("/TransactionReport")
public class TransactionReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TransactionReport() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Transaction Report Servlet....");

		// First, load JasperDesign from XML and compile it into JasperReport 
		// JasperDesign jasperDesign = JasperManager.loadXmlDesign("report/report3.jrxml");
		// JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
		ServletContext context = this.getServletConfig().getServletContext();
		System.out.println(context.getRealPath("/report/report3.jasper"));
		try {
	         /**
	          * Compile the report to a file name same as
	          * the JRXML file name
	          */
	         JasperCompileManager.compileReportToFile(context.getRealPath("/report/report3.jrxml"));
	         
	       
	        byte[] bytes =
	 				JasperRunManager.runReportToPdf("C:\\Users\\Riley\\Documents\\GitHub\\mvrc-renewal-report-component\\MVRCReportingComponent\\report\\report3.jasper", null, new JREmptyDataSource());
	 		
	        System.out.println("bytes: " + bytes.length);
	        
	        if (bytes != null) 
			{
				response.setContentType("application/pdf");
				response.setContentLength(bytes.length);
				ServletOutputStream ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
				ouputStream.close();
			} 
		
		} catch (JRException e) {
	         e.printStackTrace();
	      }
	      System.out.println("Done compiling!!! ...");
		
	}

}
