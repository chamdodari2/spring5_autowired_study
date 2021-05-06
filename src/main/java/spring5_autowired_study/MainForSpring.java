package spring5_autowired_study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring5_autowired_study.config.AppCtx;
import spring5_autowired_study.spring.ChangePasswordService;
import spring5_autowired_study.spring.DuplicateMemberException;
import spring5_autowired_study.spring.MemberInfoPrinter;
import spring5_autowired_study.spring.MemberListPrinter;
import spring5_autowired_study.spring.MemberNotFoundException;
import spring5_autowired_study.spring.MemberRegisterService;
import spring5_autowired_study.spring.RegisterRequest;
import spring5_autowired_study.spring.VersionPrinter;
import spring5_autowired_study.spring.WrongIdPasswordException;

public class MainForSpring {

	private static ApplicationContext ctx = null;

	public static void main(String[] args) throws IOException {

		ctx = new AnnotationConfigApplicationContext(AppCtx.class);

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			while (true) {
				System.out.println("명령어를 입력하세요:");
				String command = reader.readLine();

				if (command.equalsIgnoreCase("exit")) {
					System.out.println("종료합니다.");
					break;
				}
				if (command.startsWith("new ")) {
					processNewCommand(command.split(" "));
					continue;
				}
				if (command.equalsIgnoreCase("list")) {
					processListCommand();

					continue;
				}
				if (command.startsWith("change")) {
					processChangeCommand(command.split(" "));
					continue;
				}
				if (command.startsWith("info ")) {
					processInfoCommand(command.split(" "));
					continue;
				}
				if (command.equalsIgnoreCase("version")) {
					processVersionCommand();
					continue;
				}
				printHelp();
			}
		}

	}

	private static void processVersionCommand() {
		VersionPrinter versionPrinter = ctx.getBean("versionPrinter", VersionPrinter.class);
		versionPrinter.print();
	}

	private static void processListCommand() {
		MemberListPrinter listPrinter = ctx.getBean("listPrinter", MemberListPrinter.class);
		listPrinter.printAll();

	}

	private static void processInfoCommand(String[] arg) {
		if (arg.length != 2) {
			printHelp();
			return;

		}
		MemberInfoPrinter infoPrinter = ctx.getBean("infoPrinter", MemberInfoPrinter.class);
		infoPrinter.printMemberInfo(arg[1]);

	}

	private static void processNewCommand(String[] arg) {
		if (arg.length != 5) {
			printHelp();
			return;
		}
		MemberRegisterService regSvc = ctx.getBean("memberRegSvc", MemberRegisterService.class);

		RegisterRequest req = new RegisterRequest();
		req.setEmail(arg[1]);
		req.setName(arg[2]);
		req.setPassword(arg[3]);
		req.setConformpassword(arg[4]);

		if (!req.isPasswordEqualToConformPassword()) { // 패스워드랑 확인패스워드 일치하는지 확인
			System.out.println("암호와 확인이 일치하지 않습니다.\n");
			return;
		}
		try {// 맞으면 등록
			regSvc.regist(req); // regist안에는 insert있어서 그거호출된거임 문제 발생하면( 등록된 이메일 발생하면) 에러 던져주기
			System.out.println("등록했습니다.\n");
		} catch (DuplicateMemberException e) {// 강제하지않음 서비스단해서 해주는게맞음 /(AlreadyExistingMemberException
			System.out.println("이미 존재하는 이메일입니다.\n");
		}
	}

	private static void processChangeCommand(String[] arg) { // dto에 있는거처럼 네개를 어셈블러에서 가져와서, 각각 여기 넣어서
		if (arg.length != 4) {
			printHelp();
			return;
		}
		ChangePasswordService changePwdSvc = ctx.getBean("changePwdSvc", ChangePasswordService.class);
		try {
			changePwdSvc.changePassword(arg[1], arg[2], arg[3]);
			System.out.println("암호를 변경했습니다.\n");
		} catch (MemberNotFoundException e) {
			System.out.println("존재하지 않는 이메일입니다.\n");
		} catch (WrongIdPasswordException e) { // IdPasswordNotMatchingException
			System.out.println("이메일과 암호가 일치하지 않습니다.\n");
		}
	}

	private static void printHelp() {
		System.out.println("--------------------------------------");
		System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
		System.out.println("명령어 사용법:");
		System.out.println("new 이메일 이름 암호 암호확인");
		System.out.println("change 이메일 현재비번 변경비번");
		System.out.println("list");
		System.out.println("info 이메일");
		System.out.println("version");
		System.out.println("exit");
		System.out.println("--------------------------------------");
	}

}
