"use client";
import axios from 'axios';
import { useRef } from 'react';

type Props = {
    cityName: string;
    cityDescription: string;
    imageUrl: string;
    wikiUrl: string;
};

export default function GetFactForm({ cityName, cityDescription, imageUrl, wikiUrl }: Props) {
    const inputRef = useRef<HTMLInputElement>(null);

    async function handleGetFact(event: React.MouseEvent<HTMLButtonElement>): Promise<void> {
        axios.post("http://localhost:8080/api/insight/city", wikiUrl, {
            headers: {
                'Content-Type': 'text/plain'
            }
        })
        .then(response => {
            inputRef.current!.textContent = response.data;
        })
        .catch(error => console.error("Error fetching data:", error));
    }

    async function handleSaveFact(event: React.MouseEvent<HTMLButtonElement>): Promise<void> {
        axios.post("http://localhost:8080/api/insight", JSON.stringify({ 
            cityName: cityName,
            cityDescription: cityDescription,
            insightText: inputRef.current!.textContent,
            imageUrl: imageUrl,
            wikiUrl: wikiUrl
          }), {
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then()
        .catch(error => console.error("Error fetching data:", error));
    }

  return (
    <div className="grid grid-cols-4 gap-1 p-8 pt-4">
        <button 
            className="h-[50px] col-span-3 bg-black hover:bg-light-gray text-gray flex items-center justify-center rounded-bl-lg" 
            onClick={handleGetFact}>
            Get a Fact
        </button>
        <button 
            className="h-[50px] col-span-1 bg-green hover:bg-light-green text-gray flex items-center justify-center rounded-br-lg"
            onClick={handleSaveFact}>
            Save It!
        </button>
        <p ref={inputRef}
            className='col-span-4 flex items-center justify-center pt-4 italic'>
        </p>
    </div>
  )
}