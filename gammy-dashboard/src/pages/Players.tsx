import PageTitle from "@/components/pageTitle";
import PlayersList from "@/components/PlayersList";

export default function Players() {
    return <>
        <PageTitle title="Players" description="Here you can see and manage all of your game's player-base."/>

        <PlayersList/>
    </>;
}