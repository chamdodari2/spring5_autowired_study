package spring5_autowired_study.spring;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemberDao {
	
	public static long nextId= 0;
	public Map<String,Member> map = new HashMap<>();
	
	
	
	public Member selectByEmail(String email) {
		return map.get(email);
	}
	
	public void insert(Member member) {
		member.setId(++nextId);
		map.put(member.getEmail(), member);
	}
	
	public void update(Member member) {
		map.put(member.getEmail(), member);
	}

	
	
	public Collection<Member> selectAll(){  //멤버만 컬렉션안에 들어가있는거
		return map.values();
	}
	
	
	
 //  public void showMember(Member member){
	//return  map.put(member, value);}
}
