import PageTitle from "@/components/pageTitle";
import PlayerReportsList from "@/components/PlayerReportsList";

export default function PlayerReports() {
    return <>
        <PageTitle title="Player Reports" description="Here you can view the player reports made in-game."/>

        <PlayerReportsList/>
    </>;
}