<script type='text/javascript'><!-- 
$(function(){ 
 //������ʾ�� 
$('#alternation').click(function(){ 
 $('tbody > tr:odd', $('#example')).toggleClass('alternation'); 
 }); 
 //��ɫ������ʾ�� 
$('#alternationThree').click(function(){ 
 $('tbody > tr:nth-child(3n)', $('#example')).toggleClass('alternation'); 
 $('tbody > tr:nth-child(3n+2)', $('#example')).toggleClass('alternation3'); 
 }); 
 //ѡ���� 
$('#selectTr').click(function(){ 
 //Ϊ��������ѡ���¼����� 
$('tbody > tr', $('#example')).click(function(){ 
 $('.selected').removeClass('selected'); 
 $(this).addClass('selected'); //this ��ʾ�����¼��Ķ��󣬵������� jquery ���� 
}).hover( //ע���������ʽ���� 
function(){ 
 $(this).addClass('mouseOver'); 
 }, 
 function(){ 
 $(this).removeClass('mouseOver'); 
 } 
 ); 
 }); 
 //���������� 
$('#sort').click(tableSort); 
 //��ȡ�ź���������ֵ 
$('#getSequence').click(function(){ 
 var sequence = []; 
 $('#content input[name=noticeSelect]').each(function(){ 
 sequence.push(this.value); 
 }); 
 alert(sequence.join(',')); 
 }); 
 //��ȡ�������ѡ��ĸ�ѡ���ֵ���� 
$('#getSelected').click(function(){ 
 var sequence = []; 
 $('#content input[name=noticeSelect]:checked').each(function(){ 
 sequence.push(this.value); 
 }); 
 alert(sequence.join(',')); 
 }); 
 //�����ڽ������� 
$('#dateDesc').click(descByDate); 
 }); 
 //������� 
function tableSort() 
 { 
 var tbody = $('#example > tbody'); 
 var rows = tbody.children(); 
 var selectedRow; 
 //ѹ�����ʱѡȡ�� 
rows.mousedown(function(){ 
 selectedRow = this; 
 tbody.css('cursor', 'move'); 
 return false; //��ֹ�϶�ʱѡȡ�ı����ݣ������ mousemove һ��ʹ�� 
}); 
rows.mousemove(function(){ 
 return false; //��ֹ�϶�ʱѡȡ�ı����ݣ������ mousedown һ��ʹ�� 
}); 
 //�ͷ�����ʱ���в��� 
rows.mouseup(function(){ 
 if(selectedRow) 
 { 
 if(selectedRow != this) 
 { 
 $(this).before($(selectedRow)).removeClass('mouseOver'); //���� 
} 
tbody.css('cursor', 'default'); 
 selectedRow = null; 
 } 
 }); 
 //��ʾ��ǰ�������λ�� 
rows.hover( 
 function(){ 
 if(selectedRow && selectedRow != this) 
 { 
 $(this).addClass('mouseOver'); //���ִ�Сд�ģ�д�� 'mouseover' �Ͳ��� 
} 
 }, 
function(){ 
 if(selectedRow && selectedRow != this) 
 { 
 $(this).removeClass('mouseOver'); 
 } 
 } 
 ); 
 //���û�ѹ�������Ƴ� tbody ʱ����� cursor ���϶���״����ǰ��ǰѡȡ�� selectedRow 
 tbody.mouseover(function(event){ 
 event.stopPropagation(); //��ֹ tbody ���¼����������� div �� 
}); 
 $('#contain').mouseover(function(event){ 
 if($(event.relatedTarget).parents('#content')) //event.relatedTarget: ��ȡ���¼�����ǰ�������λ�ô���Ԫ�� 
{ 
tbody.css('cursor', 'default'); 
 selectedRow = null; 
 } 
 }); 
 } 
 //�����ڽ������� 
function descByDate() 
 { 
 var descElements = $('#content > tr').get().sort(function(first, second){ 
 var f = $('td:eq(4)', first).html(); //first = $('td:eq(4)', first).html();IE �»������⣬FF ��������ͬ 
var s = $('td:eq(4)', second).html(); 
 if(f < s) 
 return 1; 
 if(f == s) 
 return 0; 
 return -1; 
 }); 
 $(descElements).appendTo('#content'); 
 } 
// --></script>