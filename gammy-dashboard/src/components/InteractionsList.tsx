import { useInteractions, usePlayersQuery } from "@/lib/api/queries"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "./ui/table";

export default function InteractionsList() {
  const interactions = useInteractions();

  return interactions.data && <>
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>ID</TableHead>
          <TableHead>Interaction API Name</TableHead>
          <TableHead>Player Username</TableHead>
          <TableHead>Current Streak</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {
          interactions.data.map(interaction =>
            <TableRow>
              <TableCell>{interaction.id}</TableCell>
              <TableCell>{interaction.gameInteraction?.apiName}</TableCell>
              <TableCell>{interaction.player?.username}</TableCell>
              <TableCell>{interaction.streak}</TableCell>
            </TableRow>
          )
        }
      </TableBody>
    </Table>
  </>
}