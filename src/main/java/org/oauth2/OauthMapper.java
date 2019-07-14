package org.oauth2;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries(value = { @NamedQuery(name = "selectOauthClientDetails", query = "SELECT * FROM OAUTH_CLIENT_DETAILS") })
public interface OauthMapper {

	List selectOauthClientDetails();

}
