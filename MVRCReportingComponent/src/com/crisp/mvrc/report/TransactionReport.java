package com.crisp.mvrc.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

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
		
		ServletContext context = this.getServletConfig().getServletContext();
		Connection dbConnection;
		
		try {
			
			 Class.forName("com.mysql.jdbc.Driver");
			 
			 String url =
			            "jdbc:mysql://localhost:3306/genaudit";
					
				
						
						dbConnection =
						    (Connection) DriverManager.getConnection(url,"root", "root");	
			 
			 
	         JasperCompileManager.compileReportToFile(context.getRealPath("/report/report3.jrxml"));
	         
	       
	        byte[] bytes =
	 				JasperRunManager.runReportToPdf(context.getRealPath("/report/report3.jasper"), null, dbConnection);
	        
	        System.out.println("bytes: " + bytes.length);
	        
	        if (bytes != null && bytes.length > 800) 
			{	
	        	response.setStatus(200);
				response.setContentType("application/pdf");
				response.setContentLength(bytes.length);
				ServletOutputStream ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
				ouputStream.close();
			}else
			{	response.setStatus(200);
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Transaction Report</title>");
				out.println("</head>");

				out.println("<body>");
			
				out.println("<b>No records found.</b>");

				out.println("</body>");
				out.println("</html>");
			}
		
		} catch (Exception e) {
	         //e.printStackTrace();
			response.setStatus(422);
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Transaction Report</title>");
			out.println("</head>");

			out.println("<body>");
		
			out.println("<b>System Error Occured.</b>");

			out.println("</body>");
			out.println("</html>");
	      } 
	      System.out.println("Done compiling!!! ...");
		
	}

}
