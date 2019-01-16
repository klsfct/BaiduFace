package Class;

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
 * Servlet Filter implementation class BrowseClasses
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD, 
				DispatcherType.INCLUDE, 
				DispatcherType.ERROR
		}
					, urlPatterns = { "/Classes.jsp" , "/AddManagers.jsp" , "/CheckClass.jsp"})
public class BrowseClasses implements Filter {

    /**
     * Default constructor. 
     */
    public BrowseClasses() {
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
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;		
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		String sql = "select * from class";
		String sql2 = "select MName from major where MID like ?";
		String sql3 = "select DName from department where DID like ?";
		String ClassID = null;
		String ClassName = null;
		String Major = null;
		String MID = null;
		String Department = null;
		String DID = null;
		ArrayList<Classes> list = new ArrayList<Classes>();
		try {
			ps = con.prepareStatement(sql);
			ps2 = con.prepareStatement(sql2);
			ps3 = con.prepareStatement(sql3);
			rs = ps.executeQuery();
			while(rs.next()) {
				    ClassName = rs.getString("CName");
				    ClassID = rs.getString("CID");
				    MID = rs.getString("MID");
				    DID = rs.getString("DID");
				    ps2.setString(1 , MID);
				    rs2 = ps2.executeQuery();
				    while(rs2.next()) {
				    	Major = rs2.getString("MName");
				    }
				    ps3.setString(1 , DID);
				    rs3 = ps3.executeQuery();
				    while(rs3.next()) {
				    	Department = rs3.getString("DName");
				    }
				    list.add(new Classes(ClassID , ClassName , Major , Department));
			}
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			session.setAttribute("Clist" , list);
			chain.doFilter(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			       DBConnection.free(con , ps , rs);
			       DBConnection.free(con , ps2 , rs2);
			       DBConnection.free(con , ps3 , rs3);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub

    }
}
