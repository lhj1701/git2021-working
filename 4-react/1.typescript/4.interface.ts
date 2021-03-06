// interface : 객체 구조의 형식
// interface 타입명 {
//   속성명: 타입;
//   속성명: 타입;
// }

interface User{
  firstname:string;
  lastname?:string; // 속성명?(속성명 뒤 물음표) => 필수값이 아닌 속성
  phone?: string;
}

function printName(obj:User){
  // 삭제한 속성 반복문에서 안나옴
  // Object.keys(obj).forEach((key)=> {
    // console.log(key);
  // })
  console.log(obj.firstname + ""+obj.lastname);
}

//타입명[]
//number[],string[],User[]
function printNames(arr:User[]){
for(let obj of arr)
console.log(obj.firstname+""+obj.lastname);
}

const user : User = {
  firstname: "John",
  // lastname : "Smith"
};

// optional 필드는 delete 가능
delete user.lastname;

// 속성추가 불가함
// user.phone = "01012341999"

const users : User[]=[
  {firstname:"John", lastname:"Smith"},
  {firstname:"Gildong", lastname:"Hong"},
]

printName(user);
printNames(users);
 