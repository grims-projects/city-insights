"use client";
import Image from 'next/image'

import { garamond } from "../../layout";
import GetFactForm from '../getFactForm/GetFactForm';

type Props = {
    cityName: string;
    cityDescription: string;
    imageUrl: string;
    wikiUrl: string;
};

export default function CityCard({ cityName, cityDescription, imageUrl, wikiUrl }: Props) {
  return (
    <article className='w-screen text-black'>
        <div className='h-[300px] w-full flex items-center justify-center overflow-hidden object-scale-down'>
            <Image
            src={imageUrl}
            alt={`Picture of ${cityName}`}
            height={500}
            width={300}
            className='w-full'
            />
        </div>
        <h2 className='p-8 pt-2 pb-0'>{cityName}</h2>
        <p className={`${garamond.className} text-[20px] p-8 pt-0 pb-0`}>
            {cityDescription}
        </p>
        <a href={wikiUrl} className='p-8 pt-0 text-[15px] text-sky-600'>Wikipedia</a>
        <GetFactForm cityName={cityName} cityDescription={cityDescription} imageUrl={imageUrl} wikiUrl={wikiUrl} />
    </article>
  )
}
