1. StudentManager - inputStudent() 메소드는 학생정보를 추가하거나, 학생정보가 이미 있다면 업데이트 한다는 두가지 기능을 수행하기 때문에 메소드 하나가 한개의 기능만 담당하도록 메소드를 나눠주는 것이 좋지 않을까 생각한다. updateStudentInfo, addStudentInfo 둘로 나눠진다. 처음 유저 메뉴 선택에 따른 메소드들이 매핑될때 하나의 메소드만 한개의 메소드에 매핑 될 수 있도록 runMenu1 메소드를 새로 추가한다. 현재는 업데이트와 추가하는 메소드 각각에 사용자가 학번을 입력하지만 findStudentInDB() 메소드를 하나 빼서 runMenu1 에서 학생을 찾고, 학생을 못찾으면 새로운 학생을 추가, 학생을 찾았으면 학생정보를 업데이트 한다.  

2. readValidatedInt() 와 readValidatedString() 을 따로 두지 않고 제너릭 타입을 활용한 validateInput() 로 뺀다. 비슷한 논리적 구조의 메소드 중복을 최소한다.  readValidatedInt() 와 readValidatedString() 이 사용된 곳에서 validateInput()으로 적절하게 바꿔준다. (현재 덜 바꾼 상태  

메소드 참조형식 문법=> 클래스명::메소드명  
Integer::parseInt => Integer 클래스의 정적 메소드 parseInt

3. 
