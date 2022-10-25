/***** 타입스크립트 맛보기 *****/
/** 예제 1 **/
/*
function add(num1: number, num2: number) {
    return num1 + num2;
}

// add();
// add(1);
console.log(add(1, 2));
// add(1, 2, 3);
// add('Hello,', 'TypeScript');
*/

/** 예제 2 **/
/*
function showItems(arr:number[]) {
    arr.forEach(item => console.log(item));
}

showItems([1, 2, 3]);
// showItems(1, 2, 3);
*/

/***** 타입스크립트 타입 *****/

/*** String ***/
// let car: string = 'bmw';
// let car = 'bmw'; //타입 추론
// car = 3; // 컴파일 오류 발생

/*** number ***/
/*
let age: number = 30;
let isAdult: boolean = true;
*/

/*** 배열 ***/
/*
let a: number[] = [1, 2, 3];
let a2: Array<number> = [1, 2, 3];

let week1: string[] = ['mon', 'tue', 'wed'];
let week2: Array<string> = ['mon', 'tue', 'wed'];

// week1.push(3); //컴파일 오류 발생
*/

/*** 튜플 ***/

/*
let b: [string, number];
b = ['z', 1];
b = [1, 'z']; // 컴파일 오류 발생
*/


/*** void ***/
/*
function sayHello(): void {
    console.log('hello');
}

/!*** never: 에러 또는 무한 ***!/
function showError(): never {
    throw new Error();
}

function infLoop(): never {
    while (true) {
        //do something.
    }
}
*/

/*** enum ***/
/*
enum Os {
    WINDOW= 3,
    IOS= 10,
    ANDROID
}

console.log(Os['IOS']); // 10
console.log(Os[10]); // IOS

let myOs: Os = Os.WINDOW;
*/


/*** null, undefined ***/
/*
let a: null = null;
let b: undefined = undefined;
*/

/*** interface ***/
interface User {
    name: string,
    age: number,
    gender?: string
}

let user: User = {
    name: 'xx',
    age: 30,
}

user.gender = 'male';

console.log(user.name);
