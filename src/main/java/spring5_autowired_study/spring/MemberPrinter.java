package spring5_autowired_study.spring;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

public class MemberPrinter {//상속받아서 하위클래스도 만들어보자
	
	
	private DateTimeFormatter dateTimeFormatter;
	
	public MemberPrinter() {
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 mm월 dd일");
	}
	public void print(Member member) {  //한 멤버만 출력하는거. 리스트아님! 근데 전체멤버 출력해야하는디 멤버dao에 있는 목록을 가져와서 멤버프린틀르 그 갯수만큼 돌려주려면 새클래스 만들기 
		if(dateTimeFormatter == null) {
			System.out.printf("회원 정보 : 아이디=%s, 이메일=%s, 이름=%s, 등록일=%tF%n",
					member.getId(),member.getEmail(),member.getName(),member.getRegisterDateTime());
		}else {//주입되었으면
			System.out.printf("회원 정보 : 아이디=%s, 이메일=%s, 이름=%s, 등록일=%s%n",
					member.getId(),member.getEmail(),member.getName(),
					dateTimeFormatter.format(member.getRegisterDateTime()));
		
	}
	}
	//@Autowired/*(required = false) //이렇게 하면 안들어와도된다*/ 
	//public void setDateTimeFormatter(@Nullable DateTimeFormatter dateTimeFormatter) {
	//@Autowired(required = false)  //인수가 null 이므로 setter메서드를 호출하지 않아 생성자에서 초기화된 DataTme.. 에 의해 등록일을 출력한다 . 널러블도 가능
	@Autowired
	public void setDateTimeFormatter( @Nullable DateTimeFormatter dateTimeFormatter) { //널러블 하면 널이더라도 호출이된다. 위에 if에 맞게 들어간다.
		this.dateTimeFormatter = dateTimeFormatter;
	}
	
	
}
