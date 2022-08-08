import {render, screen} from "@testing-library/react";
import CounterView from "./CounterView";

describe('<CounterView />', () => {
    it('show the current count state ', () => {
        //조건 1
        let sampleCount = 0;
        render(<CounterView count={sampleCount}/>);
        const initialState = screen.getByText('현재 숫자: 0');

        expect(initialState).toBeInTheDocument();

        //조건 2
        sampleCount = 13;
        render(<CounterView count={sampleCount}/>);
        const countState = screen.getByText('현재 숫자: 13');

        expect(countState).toBeInTheDocument();
    });
});

