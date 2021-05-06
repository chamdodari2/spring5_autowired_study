package spring5_autowired_study.spring;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MemberListPrinter {
	
	
	private MemberDao memberDao;

	private MemberPrinter printer;

	@Autowired	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	@Autowired
	@Qualifier("printer2")
	public void setPrinter(MemberPrinter printer) {
		this.printer = printer;
	}
	
	
	
	public MemberListPrinter() {
		// TODO Auto-generated constructor stub
	}
	public MemberListPrinter(MemberDao memberDao, MemberPrinter printer) {
		this.memberDao = memberDao;
		this.printer =printer;
	}
	public void printAll() {
		Collection<Member> members = memberDao.selectAll();  //members가 멤버를 가지고있는 컬렉션인데 그걸 넘겨서
		members.forEach(m->printer.print(m));
	}
	

}
