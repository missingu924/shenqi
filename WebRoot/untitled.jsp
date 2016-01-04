<script type='text/javascript'><!-- 
$(function(){ 
 //交替显示行 
$('#alternation').click(function(){ 
 $('tbody > tr:odd', $('#example')).toggleClass('alternation'); 
 }); 
 //三色交替显示行 
$('#alternationThree').click(function(){ 
 $('tbody > tr:nth-child(3n)', $('#example')).toggleClass('alternation'); 
 $('tbody > tr:nth-child(3n+2)', $('#example')).toggleClass('alternation3'); 
 }); 
 //选择行 
$('#selectTr').click(function(){ 
 //为表格行添加选择事件处理 
$('tbody > tr', $('#example')).click(function(){ 
 $('.selected').removeClass('selected'); 
 $(this).addClass('selected'); //this 表示引发事件的对象，但它不是 jquery 对象 
}).hover( //注意这里的链式调用 
function(){ 
 $(this).addClass('mouseOver'); 
 }, 
 function(){ 
 $(this).removeClass('mouseOver'); 
 } 
 ); 
 }); 
 //增加排序功能 
$('#sort').click(tableSort); 
 //获取排好序后的主键值 
$('#getSequence').click(function(){ 
 var sequence = []; 
 $('#content input[name=noticeSelect]').each(function(){ 
 sequence.push(this.value); 
 }); 
 alert(sequence.join(',')); 
 }); 
 //获取表格中已选择的复选框的值集合 
$('#getSelected').click(function(){ 
 var sequence = []; 
 $('#content input[name=noticeSelect]:checked').each(function(){ 
 sequence.push(this.value); 
 }); 
 alert(sequence.join(',')); 
 }); 
 //按日期降序排列 
$('#dateDesc').click(descByDate); 
 }); 
 //表格排序 
function tableSort() 
 { 
 var tbody = $('#example > tbody'); 
 var rows = tbody.children(); 
 var selectedRow; 
 //压下鼠标时选取行 
rows.mousedown(function(){ 
 selectedRow = this; 
 tbody.css('cursor', 'move'); 
 return false; //防止拖动时选取文本内容，必须和 mousemove 一起使用 
}); 
rows.mousemove(function(){ 
 return false; //防止拖动时选取文本内容，必须和 mousedown 一起使用 
}); 
 //释放鼠标键时进行插入 
rows.mouseup(function(){ 
 if(selectedRow) 
 { 
 if(selectedRow != this) 
 { 
 $(this).before($(selectedRow)).removeClass('mouseOver'); //插入 
} 
tbody.css('cursor', 'default'); 
 selectedRow = null; 
 } 
 }); 
 //标示当前鼠标所在位置 
rows.hover( 
 function(){ 
 if(selectedRow && selectedRow != this) 
 { 
 $(this).addClass('mouseOver'); //区分大小写的，写成 'mouseover' 就不行 
} 
 }, 
function(){ 
 if(selectedRow && selectedRow != this) 
 { 
 $(this).removeClass('mouseOver'); 
 } 
 } 
 ); 
 //当用户压着鼠标键移出 tbody 时，清除 cursor 的拖动形状，以前当前选取的 selectedRow 
 tbody.mouseover(function(event){ 
 event.stopPropagation(); //禁止 tbody 的事件传播到外层的 div 中 
}); 
 $('#contain').mouseover(function(event){ 
 if($(event.relatedTarget).parents('#content')) //event.relatedTarget: 获取该事件发生前鼠标所在位置处的元素 
{ 
tbody.css('cursor', 'default'); 
 selectedRow = null; 
 } 
 }); 
 } 
 //按日期降序排列 
function descByDate() 
 { 
 var descElements = $('#content > tr').get().sort(function(first, second){ 
 var f = $('td:eq(4)', first).html(); //first = $('td:eq(4)', first).html();IE 下会有问题，FF 正常，下同 
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