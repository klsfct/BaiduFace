package major;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.DBConnection;

/**
 * Servlet Filter implementation class BrowseMajors
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD, 
				DispatcherType.INCLUDE, 
				DispatcherType.ERROR
		}
					, urlPatterns = { "/Majors.jsp" , "/AddClasses.jsp"})
public class BrowseMajors implements Filter {

    /**
     * Default constructor. 
     */
    public BrowseMajors() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		String id;
		String name;
		String DName = null;
		String DID;
		String sql = "select * from major";
		String sql2 = "select DName from department where DID like ?";
		ArrayList<Major> list = new ArrayList<Major>();
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				id = rs.getString("MID");
				name = rs.getString("MName");
				DID = rs.getString("DID");
		        ps = con.prepareStatement(sql2);
		        ps.setString(1 , DID);
			    rs2 = ps.executeQuery();
			    while(rs2.next()) {
			    	DName = rs2.getString("DName");
			    }
			    list.add(new Major(id , DID , name , DName));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.free(con , ps , rs);
			DBConnection.free(null , null , rs2);
		}
		HttpServletRequest req = (HttpServletRequest) request;		
		HttpSession session = req.getSession();
		session.setAttribute("Mlist" , list);
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
