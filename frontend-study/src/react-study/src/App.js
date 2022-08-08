import {useCallback, useState} from "react";
import CounterView from "./components/counter/CounterView";
import CountButtons from "./components/counter/CountButtons";

function App() {
    const [count, setCount] = useState(0);

    const incrementHandler = useCallback(() => {
        setCount((count) => count + 1);
    }, []);

    const decrementHandler = useCallback(() => {
        setCount((count) => count - 1);
    }, []);

    return (
        <>
            <CounterView count={count}/>
            <CountButtons incrementFn={incrementHandler} decrementFn={decrementHandler}/>
        </>
    );
}

export default App;
