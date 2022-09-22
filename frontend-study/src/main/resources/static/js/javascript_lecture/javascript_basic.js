/***** 1.변수 *****/
/** let과 const **/
// let name = "mike"; //재선언 불가능, 재할당 가능
// const age = 20; //재선언 불가능, 재할당 불가능
// name = "google";

//console.log(name);
//console.log(age);

/***** 2.타입 *****/
/** String **/
// const name1 = "Mike";
// const name2 = 'Mike';
// const name3 = `Mike`;
// const message1 = "I`m a boy.";
// const message2 = 'I\`m a boy.';
// const message3 = `My name is ${name}`;
// const message4 = `My age is ${age + 1}`;

// console.log(message3)
// console.log(message4)

/** Number **/
// const PI = 3.14;

// console.log(1 / 0); //Infinity
// console.log(name / 2); //NaN (Not a Number)

/** Boolean **/
// const a = true;
// const b = false;

// console.log(name == 'google');
// console.log(age > 40);

/** null과 undefined **/
// let value;
// console.log(value); //undefined
// let mike = null;
// console.log(mike); //null

/** typeof 연산자 **/
// console.log(typeof 3); //number
// console.log(typeof name); //string
// console.log(typeof true); //boolean
// console.log(typeof "XXX"); //string
// console.log(typeof null); //object (그러나 null은 객체가 아니다.)
// console.log(typeof undefined); //undefined

/** prompt, alert, confirm **/
// const name = prompt("이름을 입력하세요.");
// alert("환영합니다. " + name + "님");
// alert(`환영합니다. ${name}님`);

// const date = prompt("예약일을 입력하세요.", "2022-09-");

// const isAdult = confirm("당신은 성인 입니까?");
// console.log(isAdult);

/***** 형변환 *****/
// const math = prompt("수학 점수"); //문자형
// const english = prompt("영어 점수"); //문자형
// const total = (math + english) / 2; //자동 형변환
// alert(total);

/** String() **/
/*
console.log(
    String(3),
    String(true),
    String(false),
    String(null),
    String(undefined)
);
*/

/** Number() **/
/*
console.log(
    Number(true), //1
    Number(false), //0
    Number(null), //0
    Number("문자"), //NaN
    Number(undefined), //NaN
);
*/

/** Boolean() **/
/*
console.log(
    //false 목록 (나머지는 true)
    Boolean(0),
    Boolean(""),
    Boolean(NaN),
    Boolean(null),
    Boolean(undefined),
    Boolean(0), //false
    Boolean('0'), //true
    Boolean(''), //false
    Boolean(' '), //true
);
*/

/***** 전역변수와 지역변수 *****/

/** let **/

/* 예제1 */
/*
let msg = "Welcome";
console.log(msg);

function sayHello(name) {
    let msg = "Hello";
    console.log(`${msg} ${name}`);
}

sayHello("jsj"); //Hello jsj
*/

/* 예제2 */
/*
let name = "Mike";

function sayHello(name) {
    console.log(name);
}

sayHello(); //undefined
sayHello("Jane"); //Jane
*/

/* 예제3 */
/*
function sayHello(name) {
    let newName = name || 'friend';
    let msg = `Hello, ${newName}`;
    console.log(msg);
}

function sayHello(name = 'friend') {
    let msg = `Hello, ${name}`;
    console.log(msg);
}

sayHello(); //Hello, friend
sayHello("Jane"); //Hello, Jane
*/

/* 예제4 */
/*
function add(num1, num2) {
    return num1 + num2;
}

console.log(add(2, 3)); //5

function showError() {
    alert('에러 발생');
    return;
    alert('코드 실행 X')
}

console.log(showError()); //undefined
*/

/***** 함수 선언문 vs 함수 표현식 *****/

/** 함수 선언문 **/
/** JS 실행 전 모든 함수를 선언하는 호이스팅에 의해 선언과 호출의 순서로부터 자유롭다. **/
// 함수선언문();
// function 함수선언문() {
//     console.log('Hello');
// }

/** 함수 표현식 **/
/** 호이스팅이 되지 않기 때문에 선언과 호출 순서가 올바르지 않으면 실행되지 않는다. **/
// let 함수표현식 = function() {
//     console.log('Hello');
// }
// 함수표현식();

/** 화살표 함수 **/
/* 1.return이 존재하는 경우 */
// let add = (num1, num2) => {
//     return num1 + num2;
// }

/* 2.return이 존재하고 한 줄인 경우, return을 생략하고 {} 대신 () 사용 가능 */
// let addOneLineV1 = (num1, num2) => (
//     num1 + num2
// )

/* 3.return문이 한 줄인 경우 */
// let addOneLineV2 = (num1, num2) => num1 + num2;

/* 4.매개변수가 하나인 경우 */
// let sayHello = name => `Hello, ${name}`;

/* 5.매개변수가 없는 경우 -> 괄호 생략 X */
// let showError = () => alert('에러 발생');

/* 6.return이 존재하고 여러 줄이 존재하는 경우 {} 대신 () 사용 불가능 */
// let addMultiLine = (num1, num2) => {
//     const result = num1 + num2;
//     return result;
// }


/***** 객체 *****/
/** 선언 **/
// const superman = {
//     name: 'clark',
//     age: 33
// }

/** 접근 **/
// console.log(superman.name);
// console.log(superman['age']);

/** 추가 **/
// superman.gender = 'male';
// superman['hairColor'] = 'black';
// console.log(superman.gender);
// console.log(superman['hairColor']);

/** 삭제 **/
// delete superman.hairColor;
// console.log(superman.hairColor); //undefined

/** 단축 프로퍼티 **/
// const name = 'clark';
// const age = 33;

// const superman = {
//     name,
//     age,
//     gender: 'male',
// }

// console.log(superman)

/** 프로퍼티 존재 여부 확인 **/
// const superman = {
//     name: 'clark',
//     age: 33
// }

// console.log(superman.birthDay);
// console.log('birthDay' in superman);
// console.log('age' in superman);

/** for ... in 반복문 **/
// const superman = {
//     name: 'clark',
//     age: 33
// }

// for (let key in superman) {
//     console.log(key, superman[key]);
// }

/** 프로퍼티 in 예제 **/
// const isAdult = mike => {
//     if (!('age' in mike) || mike.age > 20) return true;
//     return false;
// }

// const mike = {
//     name: 'mike',
//     age: 18
// }

// const jane = {
//     name: 'jane'
// }

// console.log(isAdult(mike));
// console.log(isAdult(jane));

/***** 객체 method와 this *****/
/** method: 객체 프로퍼티로 할당된 함수 **/
// const superman = {
//     name: 'clark',
//     age: 33,
//     fly() {
//         console.log('날아간다.');
//     }
// }

// console.log(superman);
// superman.fly();

/** this **/
/* 함수 표현식 */
/*
const sayHello = function () {
    console.log(`Hello, I'm ${this.name}`)
};
*/

/* 화살표 함수 */
/* 일반 함수와 달리 자신만의 this를 가지지 않는다. */
/* 따라서 화살표 함수 내부에서 this 사용 시 그 this는 외부에서 값을 가져온다. */
/*
const sayHi = () => {
    console.log(`Hi, I'm ${this.name}`)
}

const mike = {
    name: 'Mike',
    sayHello,
    sayHi
}

const jane = {
    name: 'Jane',
    sayHello,
    sayHi
}

console.log(mike);
mike.sayHello();
mike.sayHi();

console.log(jane);
jane.sayHello();
jane.sayHi();

let boy = {
    name: 'mike',
    sayHello: () => {
        console.log(this); //전역객체 (브라우저 환경: window, Node.js: global)
    },
    sayHi: function () {
        console.log(this)
    },
}

boy.sayHello(); //this != boy
boy.sayHi(); //this == boy
*/

/** 객체 직접 지정하는 대신 this를 사용해야 하는 이유 **/
/*
let boy = {
    name: 'Mike',
    showName: function () {
        console.log(this.name);
    },
};

let man = boy;
man.name = "Tom";
boy.showName();
boy = null;
man.showName(); //man의 NPE 발생을 방지하기 위해 this를 사용해야 한다.
*/

/***** 배열 *****/
/* 문자, 숫자, 객체, 함수 등 포함 가능 */
/*
let arr = [
    'jsj',
    3,
    false,
    {
        name: 'messi',
        age: 30
    },
    function () {
        console.log('Test');
    }
];
*/

/** 기능 **/

/* length */
// console.log(arr.length);

/* push(): 배열 끝에 추가 */
// let days = ['월', '화', '수'];
// days.push('목');
// console.log(days);

/* pop(): 배열 마지막 요소 제거 */
// days.pop();
// console.log(days);

/* unshift(): 배열 앞에 추가 */
// days.unshift('일');
// console.log(days);

/* shift(): 배열 첫번째 요소 제거 */
// days.shift();
// console.log(days);

/* 예제 */
// days.unshift('금', '토', '일');
// console.log(days);
// days.shift();
// console.log(days);

/* for of */
// for (const day of days) {
//     console.log(day);
// }