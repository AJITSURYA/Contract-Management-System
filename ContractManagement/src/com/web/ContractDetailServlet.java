package com.web;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.model.*;
import com.service.*;
import com.utils.*;

public class ContractDetailServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		HttpSession session = null;
		session = request.getSession();
		//get user id
		Integer userId = (Integer)session.getAttribute("userId");
		
		//let user login
		if (userId == null)
		{
			response.sendRedirect("toLogin");
		}
		else
		{
			int conId = Integer.parseInt(request.getParameter("conId"));
			try 
			{
				ContractService contractService = new ContractService();
				//get the contract that is processed now
				ConDetailBusiModel conDetailBusiModel = contractService.getContractDetail(conId);
				request.setAttribute("conDetailBusiModel", conDetailBusiModel);
				request.getRequestDispatcher("/contractDetail.jsp").forward(request, response);
			}
			catch (AppException e) 
			{
				e.printStackTrace();
				response.sendRedirect("toError");
			}
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{
		this.doPost(request, response);
	}
}
