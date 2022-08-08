import {fireEvent, render, screen} from "@testing-library/react";
import CountButtons from "./CountButtons";

describe('<CountButtons />', () => {
    it('has an increment button and a decrement button', () => {
        render(<CountButtons/>);
        const incrementBtn = screen.getByTestId('incrementBtn');
        const decrementBtn = screen.getByTestId('decrementBtn');

        expect(incrementBtn).toBeInTheDocument();
        expect(decrementBtn).toBeInTheDocument();
    });

    it('calls incrementFn and decrementFn', () => {
        const incrementFn = jest.fn();
        const decrementFn = jest.fn();
        render(
            <CountButtons incrementFn={incrementFn} decrementFn={decrementFn}/>
        )
        const incrementBtn = screen.getByTestId('incrementBtn');
        const decrementBtn = screen.getByTestId('decrementBtn');

        fireEvent.click(incrementBtn);
        fireEvent.click(decrementBtn);

        expect(incrementFn).toBeCalled();
        expect(decrementFn).toBeCalled();
    })
});