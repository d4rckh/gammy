import { useInteractions, useInteractionTypes, usePlayersQuery } from "@/lib/api/queries"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "./ui/table";
import CreateOrEditGameInteractionDialog from "./interactions/CreateOrEditInteractionTypeDialog";
import DeleteInteractionTypeButton from "./interactions/DeleteInteractionTypeButton";

export default function InteractionTypesList() {
  const interactionTypes = useInteractionTypes();

  return interactionTypes.data && <>
    <Table className="mt-2">
      <TableHeader>
        <TableRow>
          <TableHead>ID</TableHead>
          <TableHead>API Name</TableHead>
          <TableHead>Display Name</TableHead>
          <TableHead>Streak Enabled</TableHead>
          <TableHead>Actions</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {
          interactionTypes.data.map(type =>
            <TableRow>
              <TableCell>{type.id}</TableCell>
              <TableCell>{type.apiName}</TableCell>
              <TableCell>{type.displayName}</TableCell>
              <TableCell>{type.streak}</TableCell>
              <TableCell className="flex flex-wrap gap-2">
                <CreateOrEditGameInteractionDialog interaction={type} />
                <DeleteInteractionTypeButton interaction={type} />
              </TableCell>
            </TableRow>
          )
        }
      </TableBody>
    </Table>
  </>
}