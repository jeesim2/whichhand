WhichHand
=========

오른손인지 왼손인지 판별해주는 모듈.



1. 클릭 이벤트시에는 엄지손가락을 사용한다는 가정하에 
   
   엄지를 화면에 꾹 눌렀을 때 변화하는 엄지중심좌표의 궤적을 가지 판별한다.
   
  
   오른손 엄지로 꾹 누를 때는 우하단에서 좌상단을 향하게 된다.
  
   왼손 엄지로 꾹 누를 때는 좌하단에서 우상단을 향하게 된다.
   
   
2. 스와이핑(페이지전환) 이벤트 시에는 손가락이 길게 움직이는 궤적을 가지고 판별한다.

  오른손 엄지로 페이지 전환할 때는 좌하단에서 우상단으로 큰 궤적을 그린다.
  
  왼손 엄지로 페이지 전환할 때는 우하단에서 좌상단으로 큰 궤적을 그린다.
  
  

위 이벤트를 수집하여 '왼손잡이' 판단 여부는 개별 앱에서 처리해주면 된다.
