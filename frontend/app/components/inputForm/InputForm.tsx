"use client";
import { FormEvent, useRef } from 'react'
import axios from 'axios';

type Props = {
    setIsButtonClicked: Function;
    setCityCardProps: Function;
};

export default function InputForm({ setIsButtonClicked, setCityCardProps }: Props) {
    const inputRef = useRef<HTMLInputElement>(null);

    async function onSubmit(event: FormEvent<HTMLFormElement>): Promise<void> {
        setIsButtonClicked(true);
        event.preventDefault();

        const city = inputRef.current ? inputRef.current.value : '';
        axios.get("http://localhost:8080/api/insight?city=" + city)
            .then(response => {
                setCityCardProps({
                    cityName: response.data.cityName,
                    cityDescription: response.data.cityDescription,
                    imageUrl: response.data.imageUrl,
                    wikiUrl: response.data.wikiUrl
                });
            })
            .catch(error => console.error("Error fetching data:", error));
    }

    return (
        <form onSubmit={onSubmit}>
            <label>
                City
                <input
                    ref={inputRef}
                    type="text" 
                    name="cityName"
                    placeholder="Stockholm" 
                    className="h-[50px] w-full bg-gray px-4 py-2 mb-2"
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
