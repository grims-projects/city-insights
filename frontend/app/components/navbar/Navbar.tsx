import Image from 'next/image'
import Link from 'next/link';

type Props = {
  setIsButtonClicked: Function;
};

export default function Navbar({ setIsButtonClicked }: Props) {
    return (
    <nav className='h-[84px] w-screen bg-gray sticky bottom-0'>
        <ul className="h-full flex justify-evenly items-center">
          <li>
            <Link href="/" onClick={() => setIsButtonClicked(false)}>
                <Image
                    src="/icons/home.svg"
                    alt="Home page button"
                    width={40}
                    height={41}
                />
            </Link>
          </li>
          <li>
            <Link href="/myfacts">
                <Image
                    src="/icons/facts.svg"
                    alt="My facts button"
                    width={60}
                    height={41}
                />
            </Link>
          </li>
        </ul>
    </nav>
    );
  }
