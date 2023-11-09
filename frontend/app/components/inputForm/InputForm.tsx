"use client";
import { FormEvent, useRef } from 'react'

export default function InputForm() {
    const inputRef = useRef<HTMLInputElement>(null);

    async function onSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        console.log("im here");

        const searchQuery = inputRef.current!.value;
        const url = `https://en.wikipedia.org/w/api.php?action=query&list=search&srsearch=${encodeURIComponent(searchQuery)}&utf8=&format=json`;

        const response = await fetch(url, {
          method: 'GET',
          mode: 'cors'
        })
     
        // Handle response if necessary
        const data = await response.json();
        // ...
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
