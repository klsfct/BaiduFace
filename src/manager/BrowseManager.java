package manager;

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
 * Servlet Filter implementation class BrowseManager
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD, 
				DispatcherType.INCLUDE, 
				DispatcherType.ERROR
		}
					, urlPatterns = { "/Manager.jsp" })
public class BrowseManager implements Filter {

    /**
     * Default constructor. 
     */
    public BrowseManager() {
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
		ResultSet rs = null;
		String name = null;
		String phone = null;
		String type = null;
		String sql = "select username , phoneNumber , flag , CName from manager a left outer join class b on a.flag = b.CID";
		ArrayList<Manager> list = new ArrayList<Manager>();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				name = rs.getString("username");
				phone = rs.getString("phoneNumber");
				type = rs.getString("CName");
				if(type == null)
					type = "学校管理员";
				list.add(new Manager(name , phone, type));
			}
			session.setAttribute("ulist" , list);
			chain.doFilter(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
            DBConnection.free(con , ps , rs);		
 		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
