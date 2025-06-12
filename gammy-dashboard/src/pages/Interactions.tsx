import PageTitle from "@/components/pageTitle";
import InteractionsList from "@/components/InteractionsList";
import InteractionTypesList from "@/components/InteractionTypes";
import CreateOrEditGameInteractionDialog from "@/components/interactions/CreateOrEditInteractionTypeDialog";

export default function Interactions() {
  return <>
    <PageTitle title="Interaction Types" description="Here you can create interaction types so you can track buttons and actions made by players in your game, you can also make a streak so the interaction will only be counted daily and also keep track of a streak count you can display in-game." />

    <CreateOrEditGameInteractionDialog />

    <InteractionTypesList />

    <PageTitle title="Player Interactions" />

    <InteractionsList />
  </>;
}