package com.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

<<<<<<< HEAD
import com.model.ConProcess;
=======
>>>>>>> origin/LiWenjie
import com.service.ContractService;
import com.utils.AppException;
import com.utils.Constant;

/**
 * Servlet for assigning contract
 */
public class AssignContractServlet extends HttpServlet {

	/**
	 * Process result of assign contrct
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set the request's character encoding
		request.setCharacterEncoding("UTF-8");
		
		// Declare session
		HttpSession session = null;
		// Get session by using request
		session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		
		// If user is not login, jump to login page
		if (userId == null) {
			response.sendRedirect("toLogin");
		}
		
		/*
		 * Get information of assign contract
		 */ 
		//Get Contract id
		int conId = Integer.parseInt(request.getParameter("conId"));
		// Get assigned cuntersign people's id 
		String[] hqht = request.getParameterValues("hqht");
		// Get assigned approver's id
		String[] spht = request.getParameterValues("spht");
		// Get assigned signer's id
		String[] qdht = request.getParameterValues("qdht");
<<<<<<< HEAD
		
		ConProcess conProcess = new ConProcess();
		conProcess.setConId(conId);
		conProcess.setUserId(userId);
		conProcess.setContent(null);
		
		
		conProcess.setState(Constant.DONE);
=======
>>>>>>> origin/LiWenjie

		try {
			//  Initialize contractService
			ContractService contractService = new ContractService();
			/*
			 * Call business logic layer to distributed contract
			 */ 
			// Assigned cuntersign people
			for (String hq : hqht) {
				contractService.distribute(conId, Integer.parseInt(hq),Constant.PROCESS_CSIGN);
			}
			
			// Assigned approver
			for (String sp : spht) {
				contractService.distribute(conId, Integer.parseInt(sp), Constant.PROCESS_APPROVE);
			}
			
			// Assigned signer
			for (String qd : qdht) {
				contractService.distribute(conId, Integer.parseInt(qd), Constant.PROCESS_SIGN);
			}
		
<<<<<<< HEAD
			contractService.distribute(conId, Integer.parseInt(userId.toString()), Constant.PROCESS_ASSIGN);
			contractService.assign(conProcess);
=======
>>>>>>> origin/LiWenjie
			// After complete assignment,redirect to page of to be distributed
			response.sendRedirect("assignContractList");
		} catch (AppException e) {
			e.printStackTrace();
			// Redirect to exception page
			response.sendRedirect("toError");
		}
	}

	/**
	 * Process GET requests
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Call doPost() to process request
		this.doPost(request, response);
	}

}
