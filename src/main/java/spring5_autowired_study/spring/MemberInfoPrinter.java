package spring5_autowired_study.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MemberInfoPrinter {
	
	private MemberDao memberDao;  //여기에 애노테이션 붙여도되고 밑에 세터에서 붙여도된다
	
	private MemberPrinter printer;  //여기에 애노테이션 붙여도되고 밑에 세터에서 붙여도된다 하고나면 APpctx에 가서 주석해줘도된다
	
	public void printMemberInfo(String email) {
		Member member = memberDao.selectByEmail(email);
		if(member==null) {
			System.out.println("데이터 없음");
			return;
		}
		printer.print(member);
		System.out.println();
	}
	@Autowired  //이렇게 메서드에 붙여도 된다
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	@Autowired  //이렇게 메서드에 붙여도 된다
	@Qualifier("printer")   // 1 또는 2 하기
	public void setPrinter(MemberPrinter printer) {
		this.printer = printer;
	}
	
	
//85쪽 키포인트는 세터메소트
}
