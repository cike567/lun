package org.oauth2;

import java.util.List;
import java.util.Map;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * 
 * @author cike
 *
 */
@NamedQueries(value = { @NamedQuery(name = "selectOauthClientDetails", query = "SELECT * FROM OAUTH_CLIENT_DETAILS"),
		@NamedQuery(name = "selectByClientId", query = "SELECT * FROM OAUTH_CLIENT_DETAILS where client_id=?"),
		@NamedQuery(name = "selectAuthorized", query = "SELECT AUTHORIZED_GRANT_TYPES FROM OAUTH_CLIENT_DETAILS where client_id=? and client_secret=?") })
public interface OauthMapper {

	/**
	 * 
	 * @return
	 */
	List selectOauthClientDetails();

	/**
	 * 
	 * @param clientId
	 * @return
	 */
	Map selectByClientId(String clientId);

	/**
	 * 
	 * @param clientId
	 * @param clientSectet
	 * @return
	 */
	String selectAuthorized(String clientId, String clientSectet);

}
