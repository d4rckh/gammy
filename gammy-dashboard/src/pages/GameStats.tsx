import PageTitle from "@/components/pageTitle";
import GameStatsList from "@/components/GameStatsList";
import CreateOrEditStatDialog from "@/components/stats/CreateOrEditStatDialog";

export default function GameStats() {
  return <>
    <PageTitle title="Stats" />

    <CreateOrEditStatDialog />

    <GameStatsList />
  </>;
}