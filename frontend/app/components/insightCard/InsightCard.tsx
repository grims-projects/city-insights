"use client";
import Image from 'next/image'
import { outfit } from "../../layout";
import { FormEvent } from 'react';

type Props = {
    cityName: string;
    imageUrl: string;
    insightText: string;
};

export default function InsightCard({ cityName, imageUrl, insightText}: Props) {
  async function onDelete(event: FormEvent<HTMLButtonElement>): Promise<void> {
    //todo
  }
  return (
    <article className='w-screen relative'>
        <div className={`absolute top-0 left-8 m-0 text-black text-2xl font-bold ${outfit.className}`}>
            {cityName}
        </div>

        <div className='h-[300px] w-full relative overflow-hidden'>
            <Image
                src={imageUrl}
                alt={`Picture of ${cityName}`}
                layout="fill"
                objectFit="cover"
                className='w-full p-8 blur-sm'
            />
            <div className='absolute inset-0 z-0 p-4 flex items-center justify-center'>
                <p className={`text-gray text-1xl font-bold p-8 text-center drop-shadow-lg ${outfit.className}`}>
                    {insightText}
                </p>
            </div>
            <button
                onClick={onDelete}
                className={`absolute bottom-0 right-0 m-8 h-[50px] w-[85px] bg-red hover:bg-dark-red text-gray flex items-center justify-center rounded-br-lg ${outfit.className}`}
            >
                Delete
            </button>
        </div>
    </article>
  )
}
