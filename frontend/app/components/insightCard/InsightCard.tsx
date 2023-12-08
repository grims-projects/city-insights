import Image from 'next/image'
import { outfit } from "../../layout";

type Props = {
    id: string;
    cityName: string;
    imageUrl: string;
    insightText: string;
    onDelete: (id: string) => void;
};

export default function InsightCard({ id, cityName, imageUrl, insightText, onDelete }: Props) {
    return (
        <article className='relative h-[500px] w-[400px] max-w-sm rounded overflow-hidden shadow-lg'>
            <div className='relative h-[50%]'>
                <Image
                    src={imageUrl}
                    alt={`Picture of ${cityName}`}
                    fill={true}
                    style={{ objectFit: "cover" }}
                />
            </div>

            <div className="px-6 py-4 text-black">
                <div className={`font-bold text-xl mb-2 ${outfit.className}`}>{cityName}</div>
                <p className={`text-gray-700 text-base ${outfit.className}`}>
                    {insightText}
                </p>
            </div>
            <div className="absolute bottom-8 right-8">
                <button 
                    onClick={() => onDelete(id)}
                >
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 24 24"
                        strokeWidth={1.5}
                        stroke="red"
                        className="w-6 h-6 active:w-5 active:h-5"
                    >
                        <path d="m14.7 9-.3 9m-4.8 0-.3-9m10-3.2 1 .2m-1-.2-1.1 13.9a2.3 2.3 0 0 1-2.3 2H8.1a2.3 2.3 0 0 1-2.3-2l-1-14m14.4 0a48.1 48.1 0 0 0-3.4-.3M3.8 6l1-.2m0 0a48.1 48.1 0 0 1 3.5-.4m7.5 0v-1c0-1.1-1-2-2.1-2.1a52 52 0 0 0-3.4 0c-1.1 0-2 1-2 2.2v.9m7.5 0a48.7 48.7 0 0 0-7.5 0" />
                    </svg>
                </button>
            </div>
        </article>
    )
}
