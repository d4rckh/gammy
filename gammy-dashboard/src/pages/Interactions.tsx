import PageTitle from "@/components/pageTitle";
import InteractionsList from "@/components/InteractionsList";
import InteractionTypesList from "@/components/InteractionTypes";
import CreateOrEditGameInteractionDialog from "@/components/interactions/CreateOrEditInteractionTypeDialog";

export default function Interactions() {
  return <>
    <PageTitle title="Game Interaction Types" />

    <CreateOrEditGameInteractionDialog />

    <InteractionTypesList />

    <PageTitle title="Player Interactions" />

    <InteractionsList />
  </>;
}