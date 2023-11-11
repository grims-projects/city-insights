"use client";
import { FormEvent, useRef, useState } from 'react'
import axios from 'axios';

type Props = {
    setIsButtonClicked: Function;
    setCityCardProps: Function;
};

export default function InputForm({ setIsButtonClicked, setCityCardProps }: Props) {
    const inputRef = useRef<HTMLInputElement>(null);
    const [isInternalLoading, setIsInternalLoading] = useState(false);

    async function handleSubmit(event: FormEvent<HTMLFormElement>): Promise<void> {
        event.preventDefault();
        setIsInternalLoading(true);
    
        const city = inputRef.current ? inputRef.current.value : '';
        try {
            const response = await axios.get(`http://localhost:8080/api/insight?city=${city}`);
            setCityCardProps({
                cityName: response.data.cityName,
                cityDescription: response.data.cityDescription,
                imageUrl: response.data.imageUrl,
                wikiUrl: response.data.wikiUrl
            });
        } catch (error) {
            console.error("Error fetching data:", error);
        } finally {
            setIsInternalLoading(false);
            setIsButtonClicked(true);
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <label>
                City
                <input
                    ref={inputRef}
                    type="text" 
                    name="cityName"
                    placeholder="Stockholm" 
                    className="h-[50px] w-full bg-gray text-black px-4 py-2 mb-2"
                />
            </label>
            <input 
                type="submit" 
                value="Get Location"
                className="h-[50px] w-full bg-black hover:bg-light-gray text-gray flex items-center justify-center rounded-b-lg"
            />
        </form>
    );
  }
