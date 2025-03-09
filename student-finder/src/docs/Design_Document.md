# Student Finder 설계 문서 (Design Document)
클래스와 인터페이간 관계와 역할, 필드와 메소드 목록 정리 및 역할을 설명하는 문서이다.

##  클래스 및 인터페이스 설명

### ObjectIO
[**역할**]\

[**필드**]

[**메소드**]

### StudentDBIO
class extends ObjectIO implements StudentIO\

[**역할**]\
ObjectIO 클래스를 상속 받고 StudentIO를 구현하는 클래스.\


[**필드**]

[**메소드**]

### StudentManager
[**역할**]\
StudentDBIO 클래스를 상속 받는 클래스.\
Student 객체에 대한 정보를 입력, 삭제, 조회 기능을 제공한다.

[**필드**]
StudentDAO studentDAO : 데이터베이스와 소통할 객체이다.
Pattern SNO_PATTERN : 자바의 Pattern 타입 - 학번 입력값이 10자리 숫자인지 검증한다.
Pattern NAME_PATTERN : 자바의 Pattern 타입 - 한글 또는 영어 문자만 있는지 검증한다.
Map<Integer, Runnable> menuChoice : 선택되는 메뉴 번호와 그에 따라 실행할 메소드를 매핑하는 해쉬맵이다.

[**메소드**]
void initializeMenu : 사용자가 입력하는 숫자에 따라 실행될 메소드를 처음으로 매핑한다.\
void exitApp : 프로그램을 강제 종료한다.\
void startMenuLoop : 사용자가 원하는 기능을 번호로 입력받는 동작을 무한 반복한다. 5번 프로그램 종료가 입력값이되면 프로그램을 종료한다.\
void printMenu: 사용자가 선택할 수 있는 메뉴정보를 출력한다.\
String validateString : 사용자가 입력한 값이 정규표현식 패턴과 일치하는지 검증한다. 검증을 통과하면 입력된 값을 반환한다.\
int validateInt : 사용자가 입력한 값이 정수인지 검증한다. 검증을 통과하면 입력된 값을 반환한다.\

void inputStudent :  입력된 학생정보가 데이터 베이스에 없으면 입력값이 바른 값인지 검증 한 후 데이터 베이스에 저장한다.\
입력된 학생정보가 데이터베이스에 있으면 데이터베이스에 있는 학생정보를 업데이트 한다. 사용자가 1,2,3 을 입력하느냐에 따라 학생에 대한 정보를 모드 업데이트 하거나, 과목 점수만 업데이트 하거나, 데이터베이스에 저장하는 동작을 완전히 빠져나갈 수 있다.

private void updateInMemoryStudent : students 리스트에서 입력된 학번과 같은 학번을 가진 학생 객체가 있다면 삭제한다.

### Student
[**역할**]\
학생 객체가 가질 수 있거나 수행할 수 있는 기능을 가지는 클래스이다.

[**필드**]
String sno :  학생 학번\
String name :  학생 이름\
List<Subject> subjects : 과목 객체를 담는 리스트

[**메소드**]
int getTotal : subjects 리스트 안의 과목 개체들의 총 점수를 계산한다. 총점수를 반환한다.\
double getAverage : subjects 리스트가 비었는지 검사하여 거짓이면 과목개체들의 평균을 계산한다. 평균을 반환한다.

String computeGrade : 학생 개체의 평균 과목 점수의 범위에 대한 등급을 계산한다. 등급을 반환한다.\

[**정적 이너 클래스 Subject**]\
Student 클래스의 정적 이너 클래스이다. 과목 개체에 대한 정보를 담는다.\

private String name : 과목의 이름.\
private int score : 과목의 점수.\

[**정적 이너 클래스 StudentBuilder**]\
학생 개체 생성에 대해 빌더 패턴을 적용하기 위한 클래스이다.\
이때 이너 클래스의 필드는 private 으로 설정한다. 데이터의 불변성, 유효성, 무결성, 객체의 캡슐화 때문이다.\

private String sno : 학생의 학번\
private String name : 학생의 이름 \
private List<Subject> subjects : 학생이 시험을 친 과목들을 담는 리스트. \

*빌더의 메소드들은 외부에서 학생 객체를 생성할 수 있도록 public으로 설정한다.*
public StudentBuilder sno(String sno) : 빌더의 학번 필드에 학번을 설정하고 빌더 객체를 반환한다.  
public StudentBuilder  name(String name) : 빌더의 이름 필드에 이름을 설정하고 빌더 객체를 반환한다.  
public StudentBuilder addSubjects(String subjectName, int score) : 빌더의 subjects ArrayList에 과목 객체를 저장한다.

### StudentDAO
[**역할**]\

[**필드**]

[**메소드**]

### StudentIO
[**역할**]\

[**필드**]

[**메소드**]

### SearchStudent
[**역할**]\

[**필드**]

[**메소드**]
### SortedStudent
[**역할**]\

[**필드**]

[**메소드**]
### InputStudent
[**역할**]\

[**필드**]

[**메소드**]

### StudentOutput
[**역할**]
outputStudent() 추상 메소드를 선언하는 인터페이스.

[**필드**]\
None.

[**메소드**]
void outputStudent();