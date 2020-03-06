package member;

import java.util.List;

public interface MemberDao {
	int insert(Member member);

	int update(Member member);

	Member findByMemberId(int member_Id);

	List<Member> getAll();

	String login(String account, String password);
	
	boolean forget(String account,String phone);
	
	int updatePassword(Member account);
}

