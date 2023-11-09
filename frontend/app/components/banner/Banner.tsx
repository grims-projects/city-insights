import Image from 'next/image'

export default function Banner() {
    return (
        <Image
            src="/images/logo.svg"
            alt="Logo image"
            width={172}
            height={157}
        />
    );
  }
