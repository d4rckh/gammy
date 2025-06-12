import { usePlayerParametersOverrides, usePlayerStats } from "@/lib/api/queries";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "../ui/table";
import DeleteOverrideButton from "./overrides/DeleteOverrideButton";

export function PlayerOverrides({ playerId }: { playerId: number }) {
  const {data} = usePlayerParametersOverrides(playerId);

  return data && <>
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>Parameter Path</TableHead>
          <TableHead>Old Value -&gt; New Value</TableHead>
          <TableHead>Actions</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {
          data.map(item =>
            <TableRow>
              <TableCell>{item.parameter?.path}</TableCell>
              <TableCell>{item.parameter?.value} -&gt; {item.value}</TableCell>
              <TableCell>
                <DeleteOverrideButton override={item} />
              </TableCell>
            </TableRow>
          )
        }
      </TableBody>
    </Table>
  </>
}